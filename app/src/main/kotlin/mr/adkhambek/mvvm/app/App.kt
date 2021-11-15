package mr.adkhambek.mvvm.app

import androidx.multidex.MultiDexApplication
import com.mocklets.pluto.Pluto
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        Pluto.initialize(this)
    }
}