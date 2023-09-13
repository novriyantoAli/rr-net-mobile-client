package net.coblos.rrnet.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import net.coblos.rrnet.R
import net.coblos.rrnet.databinding.ActivityMainBinding
import net.coblos.rrnet.domain.session.SessionManager

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sessionManager = SessionManager(this)
        if (!sessionManager.isLoggedIn()) {
            viewModel.logout()
        } else {
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
            navView.visibility = if(des.id == R.id.navigation_login) {
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