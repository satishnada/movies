package com.movie.ui.home.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movie.base.BaseFragment
import com.movie.databinding.FragmentMoviesListBinding
import com.movie.viewmodel.MoviesViewModel
import com.movie.xutil.*
import kotlinx.android.synthetic.main.fragment_movies_list.*


/**
 * A simple [Fragment] subclass.
 */
class MoviesFragment : BaseFragment() {

    private var loadMore: Boolean = false
    private lateinit var moviesAdapter: MoviesAdapter

    private val viewModel: MoviesViewModel by kodeinViewModel()

    private lateinit var binding: FragmentMoviesListBinding
    private var page = 1L
    private var totalPage = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initMoviesAdapter()
        fetchMovies()
        waitForTransition(binding.rvMovies)
    }

    private fun fetchMovies() {
        binding.progressBar.show()

        viewModel.result.observe(viewLifecycleOwner, Observer {
            totalPage = it.totalPages
            binding.progressBar.hide()
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            binding.progressBar.hide()
            root_layout.showSnackbar(it)
        })

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            dLog<MoviesFragment>("Movies")
            if (loadMore) {
                moviesAdapter.removeLoadMoreItem()
                loadMore = false
            }
            moviesAdapter.addData(it)
        })

        viewModel.getMovies(AppConstants.MOVIES_POPULAR, page)
    }

    private fun initMoviesAdapter() {
        moviesAdapter = MoviesAdapter()
        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = moviesAdapter
            addOnScrollListener(onScroll)
        }
    }

    private val onScroll = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val lm = binding.rvMovies.layoutManager as LinearLayoutManager

            val lastPage = page.toInt() >= totalPage

            val lastItemPosition =
                lm.findLastVisibleItemPosition() == (moviesAdapter.itemCount - 1)

            if (!loadMore && !lastPage && lastItemPosition) {
                page += 1
                loadMore = true
                moviesAdapter.addLoadMoreItem()
                viewModel.getMovies(AppConstants.MOVIES_POPULAR, page)
            } else if (lastPage && lastItemPosition) {
                root_layout.showSnackbar("No more data")
                moviesAdapter.addNoMoreItem()
                rvMovies.removeOnScrollListener(this)
            }
        }
    }
}
