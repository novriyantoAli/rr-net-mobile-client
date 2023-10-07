package net.coblos.rrnet.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import net.coblos.rrnet.R
import net.coblos.rrnet.databinding.ActivityMainBinding
import net.coblos.rrnet.domain.session.SessionManager
import net.coblos.rrnet.utils.BackgroundWorker
import net.coblos.rrnet.utils.BaseActivity
import net.coblos.rrnet.utils.WorkHandler
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WorkHandler.workInfo.observe(this) {

        }

        val sessionManager = SessionManager(this)
        if (!sessionManager.isLoggedIn()) {
            viewModel.logout()
        } else {
            //create work
            viewModel.login(sessionManager.getUserIdFromSession()!!)
        }

        val data: Uri? = intent.data
        val scheme: String? = data?.scheme // "rr.net.scheme"

        val host: String? = data?.host // "coblos.network"

        val params: List<String>? = data?.pathSegments
        val server = params?.get(0) // "status"

        val port = params?.get(1) // "1234"

        if (scheme != null && host != null && server != null && port != null) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(scheme)
            builder.setMessage("host: $host\nserver: $server\nport: $port\n\n")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Save") { dialog, _ ->
                // save to session
                sessionManager.setUrl(host)
                sessionManager.setServer(server)
                sessionManager.setServerPort(port)
                Toast.makeText(
                    this@MainActivity,
                    "Configuration saved...",
                    Toast.LENGTH_SHORT
                ).show()

                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)

            alertDialog.show()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.addOnDestinationChangedListener { _, des, _ ->
            Log.e("DESTINATION", des.displayName)
            navView.visibility = if(des.id == R.id.navigation_otp_mobile || des.id == R.id.navigation_otp_verification) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}