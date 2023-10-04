package net.coblos.rrnet.ui.otp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import net.coblos.rrnet.R
import net.coblos.rrnet.databinding.FragmentOtpVerificationBinding
@AndroidEntryPoint
class OtpFragmentVerification : Fragment() {

    private var _binding: FragmentOtpVerificationBinding? = null
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOtpVerificationBinding.inflate(inflater, group, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}