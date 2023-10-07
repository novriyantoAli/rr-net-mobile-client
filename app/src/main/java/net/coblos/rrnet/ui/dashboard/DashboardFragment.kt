package net.coblos.rrnet.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import net.coblos.rrnet.R
import net.coblos.rrnet.databinding.FragmentDashboardBinding
import net.coblos.rrnet.ui.MainViewModel

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View {
        val navController = findNavController()
        viewModel.dataState.observe(viewLifecycleOwner) {
            if (it != null) {
            } else {
                navController.navigate(R.id.navigation_otp_mobile)
            }
        }
        _binding = FragmentDashboardBinding.inflate(inflater, group, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}