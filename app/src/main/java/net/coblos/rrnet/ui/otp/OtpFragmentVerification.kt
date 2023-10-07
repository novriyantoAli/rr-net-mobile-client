package net.coblos.rrnet.ui.otp

import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import net.coblos.rrnet.R
import net.coblos.rrnet.databinding.FragmentOtpVerificationBinding
import net.coblos.rrnet.domain.DataState
import net.coblos.rrnet.domain.session.SessionManager
import net.coblos.rrnet.ui.MainViewModel


@AndroidEntryPoint
class OtpFragmentVerification : Fragment() {

    private var _binding: FragmentOtpVerificationBinding? = null
    private val binding get() = _binding
    private val viewModel: MainViewModel by activityViewModels()

    private val sessionManager = SessionManager(requireContext())

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOtpVerificationBinding.inflate(inflater, group, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val otp = binding?.otp
        val otpBox1 = binding?.otpBox1
        val otpBox2 = binding?.otpBox2
        val otpBox3 = binding?.otpBox3
        val otpBox4 = binding?.otpBox4
        val otpBox5 = binding?.otpBox5
        val otpBox6 = binding?.otpBox6
        val verify = binding?.verify

        otp?.text = Html.fromHtml(resources.getString(R.string.otp1))

        viewModel.dataStatePostVerification.observe(viewLifecycleOwner){ postVerify ->
            when(postVerify){
                is DataState.Loading -> {
                    // show progress bar
                }
                is DataState.Success -> {
                    if (postVerify.data != null) {
                        // set data to sessionManager
                        sessionManager.createLoginSession(
                            id = postVerify.data.id,
                            name = postVerify.data.name,
                            mobile = postVerify.data.mobile,
                            token = postVerify.data.token,
                            isRemOn = true
                        )
                        // navigate to home
                        findNavController().navigate(R.id.navigation_home)
                    }
                }
                is DataState.Error -> {
                    Toast.makeText(
                        requireContext(), postVerify.exception.message, Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    Toast.makeText(
                        requireContext(), "unknown error", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        otpBox1?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable?) {
                if (editable?.length == 1) otpBox2?.requestFocus()
            }
        })
        otpBox2?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length == 1) otpBox3?.requestFocus()
            }
        })
        otpBox3?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length == 1) otpBox4?.requestFocus()
            }
        })
        otpBox4?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length == 1) otpBox5?.requestFocus()
            }
        })
        otpBox5?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length == 1) otpBox6?.requestFocus()
            }
        })

        verify?.setOnClickListener {
            if (otpBox1?.length() != 1) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.message_otp_error),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (otpBox2?.length() != 1) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.message_otp_error),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (otpBox3?.length() != 1) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.message_otp_error),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (otpBox4?.length() != 1) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.message_otp_error),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (otpBox5?.length() != 1) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.message_otp_error),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (otpBox6?.length() != 1) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.message_otp_error),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val otpString = otpBox1.text.toString() + otpBox2.text.toString() + otpBox3.text.toString() + otpBox4.text.toString() + otpBox5.text.toString() + otpBox6.text.toString()
            val path = sessionManager.getServerURL() + resources.getString(R.string.path_api_sign_verify)
            viewModel.postVerification(path, otpString)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}