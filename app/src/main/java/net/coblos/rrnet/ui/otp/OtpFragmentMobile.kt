package net.coblos.rrnet.ui.otp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import net.coblos.rrnet.R
import net.coblos.rrnet.databinding.FragmentOtpMobileBinding
import net.coblos.rrnet.domain.DataState
import net.coblos.rrnet.domain.session.SessionManager
import net.coblos.rrnet.ui.MainViewModel

@AndroidEntryPoint
class OtpFragmentMobile : Fragment() {

    private var _binding: FragmentOtpMobileBinding? = null
    private val binding get() = _binding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var savedStateHandle: SavedStateHandle
    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOtpMobileBinding.inflate(inflater, group, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mobileNumber = binding?.mobileNumber
        val send = binding?.generateOtp
        viewModel.dataStatePostMobile.observe(viewLifecycleOwner){ postMobile ->
            when(postMobile){
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    Toast.makeText(
                        requireContext(), postMobile.data.message, Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(
                        R.id.action_fragmentOtpMobile_to_fragmentOtpVerification
                    )
                }
                is DataState.Error -> {
                    Toast.makeText(
                        requireContext(), postMobile.exception.message, Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    Toast.makeText(
                        requireContext(), "unknown error", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        send?.setOnClickListener {
            if (mobileNumber?.text.toString().isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.message_mobile_number_cannot_empty),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (mobileNumber?.text.toString().isBlank()) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.message_mobile_number_cannot_empty),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (mobileNumber?.text.toString().length < 9) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.message_invalid_mobile_number),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val sessionManager = SessionManager(requireContext())
            val path = sessionManager.getServerURL() + resources.getString(R.string.path_api_sign_up)
            viewModel.postMobile(path, mobileNumber?.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}