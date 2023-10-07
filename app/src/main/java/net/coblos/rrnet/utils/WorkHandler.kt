package net.coblos.rrnet.utils

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import net.coblos.rrnet.App
import java.util.concurrent.TimeUnit

object WorkHandler {
    private const val tag = "WorkHandler"
    private val workManager = WorkManager.getInstance(App.appInstance)

    internal val workInfo: LiveData<List<WorkInfo>> = workManager.getWorkInfosByTagLiveData(tag)

    /**
     * create work request
     *
     * @param delay
     */
    fun createWork(delay: Long) {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(true)
            .build()

        val request = OneTimeWorkRequestBuilder<BackgroundWorker>()

        val work = request.setConstraints(constraints)
            .addTag(tag)
            .setInitialDelay(delay, TimeUnit.MINUTES)
            .build()

        workManager.enqueue(work)

        Log.i(tag, "work created")
    }

    /**
     * do UI related work because this method is supporting foreground work
     * If you have any other requirement then please update the conditions
     */
    fun onDoWork() {
        try {
            val activity = BaseActivity.currentActivityInstance.get()

            if (activity?.lifecycle?.currentState?.isAtLeast(Lifecycle.State.RESUMED) == true) {
                //if current activity is active
                //do UI related work
                Log.i(tag, "app is active, do UI work")

            }
            //recreate the work if required
            Log.i(tag, "recreateWork")

            createWork(WORK_DELAY_IN_MIN)
//            else {
//                //cancel work if app got killed, if you want to continue then remove this condition
//                //and, keep recreate work logic
//                Log.i(tag, "safe: cancel work if any")
//                cancelWork()
//            }

        } catch (ex: Exception) {
            Log.e(tag, ex.toString())
        }
    }

    /**
     * cancel enqueued work by the given tag
     */
    fun cancelWork() {
        workManager.cancelAllWorkByTag(tag)

        Log.i(tag, "work canceled")
    }

}