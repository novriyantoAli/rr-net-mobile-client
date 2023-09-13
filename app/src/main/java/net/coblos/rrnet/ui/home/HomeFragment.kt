package net.coblos.rrnet.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import dagger.hilt.android.AndroidEntryPoint
import net.coblos.rrnet.databinding.FragmentHomeBinding
import net.coblos.rrnet.domain.session.SessionManager

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View {

        _binding = FragmentHomeBinding.inflate(inflater, group, false)
        val root: View = binding.root

        val username = binding.username
        val password = binding.password
        val login = binding.login

        val sessionManager = SessionManager(requireContext())
        login.setOnClickListener {
            viewModel.login(sessionManager.getUrl()!!, username.text.toString(), password.text.toString())
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}