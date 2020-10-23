package mr.adkhambek.mvvm.utils

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


class LocationUtil(
    private val context: Context,
    lifecycle: Lifecycle
) : LifecycleObserver {

    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Toast.makeText(context, "onResume", Toast.LENGTH_LONG).show()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Toast.makeText(context, "onPause", Toast.LENGTH_LONG).show()
    }
}