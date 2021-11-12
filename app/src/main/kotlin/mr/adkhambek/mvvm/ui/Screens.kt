@file:Suppress("FunctionName")

package mr.adkhambek.mvvm.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import mr.adkhambek.mvvm.ui.home.HomeFragment

object Screens {

    fun Home(): FragmentScreen = FragmentScreen {
        HomeFragment()
    }
}