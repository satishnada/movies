package com.movie.ui.home.movies

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.movie.R
import com.movie.data.local.entities.MovieEntity
import com.movie.databinding.ItemLoadMoreDataBinding
import com.movie.databinding.ItemMovieBinding
import com.movie.databinding.ItemNoMoreDataBinding
import com.movie.xutil.Coroutines

class MoviesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movieList: ArrayList<MovieEntity> = ArrayList()
    //val itemClickObservable = MutableLiveData<MovieEntity>()

    companion object {
        const val NO_MORE_ITEM = -1
        const val LOAD_MORE_ITEM = 0
        const val MOVIE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LOAD_MORE_ITEM -> {
                LoadMoreItemViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_load_more_data,
                        parent,
                        false
                    ) as ItemLoadMoreDataBinding
                )
            }
            MOVIE_ITEM -> {
                MovieItemViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_movie,
                        parent,
                        false
                    ) as ItemMovieBinding
                )
            }
            NO_MORE_ITEM -> {
                NoMoreItemViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_no_more_data,
                        parent,
                        false
                    ) as ItemNoMoreDataBinding
                )
            }
            else -> {
                NoMoreItemViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_no_more_data,
                        parent,
                        false
                    ) as ItemNoMoreDataBinding
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun getItemViewType(position: Int): Int {
        movieList[position].viewType?.let { viewType ->
            return viewType
        }
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder) {
            is MovieItemViewHolder -> {
                viewHolder.bindView(movieList[position])
            }
        }
    }

    fun addData(movieEntities: List<MovieEntity>) {
        val oldItemCount = movieList.size
        movieList.addAll(movieEntities)
        notifyItemRangeInserted(oldItemCount, movieList.size)
    }

    fun addLoadMoreItem() {
        val movieEntity = MovieEntity()
        movieEntity.viewType = LOAD_MORE_ITEM
        movieList.add(movieEntity)
        Coroutines.main {
            notifyItemInserted(movieList.size)
        }
    }

    fun addNoMoreItem() {
        val movieEntity = MovieEntity()
        movieEntity.viewType = NO_MORE_ITEM
        movieList.add(movieEntity)
        Coroutines.main {
            notifyItemInserted(movieList.size)
        }
    }

    fun removeLoadMoreItem() {
        movieList.removeAt(movieList.size - 1)
        notifyItemRemoved(movieList.size)
    }

    inner class MovieItemViewHolder(private val itemMovieBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemMovieBinding.root) {

        fun bindView(movieEntity: MovieEntity) {
            itemMovieBinding.movieEntity = movieEntity
            itemMovieBinding.cvItem.setOnClickListener {
                //itemClickObservable.value = movieEntity
                val direction = MoviesFragmentDirections.toMovieDetail(movieEntity)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val extras = FragmentNavigatorExtras(
                        itemMovieBinding.ivPoster.toTransitionGroup()!!,
                        itemMovieBinding.tvTitle.toTransitionGroup()!!,
                        itemMovieBinding.tvOverview.toTransitionGroup()!!
                    )
                    it.findNavController().navigate(direction, extras)
                } else {
                    it.findNavController().navigate(direction)
                }
            }
            itemMovieBinding.executePendingBindings()
        }
    }

    inner class LoadMoreItemViewHolder(itemLoadMoreBinding: ItemLoadMoreDataBinding) :
        RecyclerView.ViewHolder(itemLoadMoreBinding.root)

    inner class NoMoreItemViewHolder(itemNoMoreDataBinding: ItemNoMoreDataBinding) :
        RecyclerView.ViewHolder(itemNoMoreDataBinding.root)
}