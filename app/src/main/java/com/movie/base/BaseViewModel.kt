package com.movie.base

import androidx.lifecycle.ViewModel
import com.movie.xutil.SingleLiveEvent
import com.movie.xutil.dLog
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext = job
    val onCounting = SingleLiveEvent<Int>()
    val onCountFinish = SingleLiveEvent<Unit>()
    private var counting = false

    fun mainCoroutineScope(work: suspend (() -> Unit)) =
        CoroutineScope(coroutineContext + Dispatchers.Main).launch {
            work()
        }

    fun ioCoroutineScope(work: suspend (() -> Unit)) =
        CoroutineScope(coroutineContext + Dispatchers.IO).launch {
            work()
        }

    fun defaultCoroutineScope(work: suspend (() -> Unit)) =
        CoroutineScope(coroutineContext + Dispatchers.Default).launch {
            work()
        }

    fun startCounter(timeOut: Int) {
        if (counting) {
            return
        }
        mainCoroutineScope {
            for (i in timeOut downTo 0) {
                if (i > 0) {
                    onCounting.value = i
                    counting = true
                } else {
                    onCountFinish.value = Unit
                    counting = false
                }
                delay(999)
            }
        }
    }

    override fun onCleared() {
        coroutineContext.cancel(CancellationException("View mode cleared!!"))
        dLog<BaseViewModel>("onCleared")
        super.onCleared()
    }
}