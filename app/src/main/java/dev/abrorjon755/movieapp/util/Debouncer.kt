package dev.abrorjon755.movieapp.util

import android.os.Handler
import android.os.Looper

class Debouncer(private val delayMillis: Long) {
    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null

    fun debounce(action: () -> Unit) {
        runnable?.let { handler.removeCallbacks(it) }
        runnable = Runnable { action() }
        handler.postDelayed(runnable!!, delayMillis)
    }
}