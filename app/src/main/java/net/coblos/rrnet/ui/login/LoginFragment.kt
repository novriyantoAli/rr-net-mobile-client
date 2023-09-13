package net.coblos.rrnet.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import net.coblos.rrnet.databinding.FragmentLoginBinding
import net.coblos.rrnet.domain.DataState
import net.coblos.rrnet.domain.session.SessionManager
import net.coblos.rrnet.ui.MainViewModel

@AndroidEntryPoint
class LoginFragment : Fragment() {

    companion object {
        const val LOGIN_SUCCESSFUL: String = "LOGIN_SUCCESSFUL"
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var savedStateHandle: SavedStateHandle

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View {

      _binding = FragmentLoginBinding.inflate(inflater, group, false)
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle
        savedStateHandle[LOGIN_SUCCESSFUL] = false

        val usernameEditText = binding.username
        val passwordEditText = binding.password
        val loginButton = binding.login
        val verifyButton  = binding.verify
        val loadingProgressBar = binding.loading

        viewModel.dataStatePostMobile.observe(viewLifecycleOwner) { postMobile ->
            when(postMobile) {
                is DataState.Loading -> {
                    loadingProgressBar.visibility = View.VISIBLE
                }
                is DataState.Success -> {
                    loadingProgressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(), "please input code in your message", Toast.LENGTH_LONG
                    ).show()
                }
                is DataState.Error -> {
                    Toast.makeText(
                        requireContext(), postMobile.exception.message, Toast.LENGTH_SHORT
                    ).show()
                    loadingProgressBar.visibility = View.GONE
                }
                else -> {
                    Toast.makeText(
                        requireContext(), "response cannot determine...", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        loginButton.setOnClickListener {
            postMobile(usernameEditText.text.toString())
        }

        verifyButton.setOnClickListener {
            postVerification()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun postMobile(s: String) {
        val sessionManager = SessionManager(requireContext())
        viewModel.postMobile(sessionManager.getServerURL(), s)
    }

    private fun postVerification(m: String, v: String){
        val sessionManager = SessionManager(requireContext())
        viewModel.postVerification(sessionManager.getServerURL(), m, v)
    }
}