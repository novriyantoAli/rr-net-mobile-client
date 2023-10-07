package net.coblos.rrnet.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.InetAddresses
import android.net.NetworkCapabilities
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import net.coblos.rrnet.domain.session.SessionManager
import java.net.Inet4Address
import java.net.InetAddress
import java.net.InetSocketAddress

class BackgroundWorker(private val appContext: Context, workerParameters: WorkerParameters): Worker(appContext, workerParameters) {

    override fun doWork(): Result {
        val tag = "BackgroundWorker"
        Log.i(tag, "doWork")
        Log.i(tag, "checking connectivity...")

        // check if using wifi
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_CAPTIVE_PORTAL)) {
            Log.i(tag, "wifi connection detected and using captive portal")
            val sessionManager = SessionManager(appContext)
            if (sessionManager.isLoggedIn()) {

            }
        }

        //you can notify back to user
        WorkHandler.onDoWork()

        //or if you need to do some background task e.g., upload or download
        //then pass the reference of the required resources in MyWorker()
        doFurtherTask()
        return Result.success()
    }

    private fun doFurtherTask() {
        val tag = "doFurtherTask()"

        // do your background work here if required
    }
}