package net.coblos.rrnet.utils

import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference

open class BaseActivity: AppCompatActivity() {
    companion object {
        var currentActivityInstance: WeakReference<AppCompatActivity?> =
            WeakReference<AppCompatActivity?>(null)
    }

    override fun onResume() {
        super.onResume()
        currentActivityInstance = WeakReference(this)
    }
}