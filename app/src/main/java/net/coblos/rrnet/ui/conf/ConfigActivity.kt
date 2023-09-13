package net.coblos.rrnet.ui.conf

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.coblos.rrnet.R

class ConfigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        val intent = Intent("net.coblos.rrnet.from.mobile")
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        val bundle = Bundle()
        bundle.putString("msg_from_browser", "Launched from Browser")
        intent.putExtras(bundle)
    }
}