package net.coblos.rrnet

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import net.coblos.rrnet.utils.WORK_DELAY_IN_MIN
import net.coblos.rrnet.utils.WorkHandler

@HiltAndroidApp
class App: Application(){
    init {
        appInstance = this
    }

    companion object {
        lateinit var appInstance: App
    }

    override fun onCreate() {
        super.onCreate()

        //start work on application start
        //or you can start work as per your need
        //e.g., from MainActivity, SplashActivity, via user action, etc.
        WorkHandler.createWork(WORK_DELAY_IN_MIN)
    }
}