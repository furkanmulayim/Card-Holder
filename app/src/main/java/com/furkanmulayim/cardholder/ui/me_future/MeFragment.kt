package com.furkanmulayim.cardholder.ui.me_future

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.furkanmulayim.cardholder.R
import com.furkanmulayim.cardholder.databinding.FragmentMeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MeFragment : Fragment() {

    private lateinit var binding: FragmentMeBinding
    private lateinit var viewModel: MeFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_me, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this)[MeFragmentViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickedEditButtons()
        observeTcCard()
        passwordUpdate()
    }

    private fun clickedEditButtons() {
        binding.tcDriveLicenceCard.setOnClickListener {
            it.isClickable = false
            it.startAnimation(AnimationUtils.loadAnimation(context, R.anim.recyc_click))
            CoroutineScope(Dispatchers.Main).launch {
                delay(180)
                Navigation.findNavController(requireView()).navigate(R.id.action_meFragment_to_driverLicenceFragment)
            }
        }
    }

    private fun observeTcCard() {

        binding.tcrcyc.layoutManager = LinearLayoutManager(requireContext())
        viewModel.tcList.observe(viewLifecycleOwner) {
            val adapter = TcListAdapter(requireContext(), it, viewModel)
            binding.tcrcyc.adapter = adapter

            if (adapter.itemCount > 0) {
                binding.tcDriveLicenceCard.visibility = View.GONE
            } else {
                binding.tcDriveLicenceCard.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTcList()
    }

    fun passwordUpdate() {
        viewModel.passUpdate(
            binding.buttonPassUpdate, requireView(), R.id.action_meFragment_to_passUpdateFragment
        )
    }


}