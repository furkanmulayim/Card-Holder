package com.furkanmulayim.cardholder.ui.pass_update_future

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.furkanmulayim.cardholder.R
import com.furkanmulayim.cardholder.databinding.FragmentPassUpdateBinding

class PassUpdateFragment : Fragment() {

    private lateinit var binding: FragmentPassUpdateBinding
    private lateinit var viewModel: PassUpdateFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pass_update, container, false)

        viewModel = ViewModelProvider(this)[PassUpdateFragmentViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passwordUpdate()
        viewModel.popBackStack(binding.backTextButton,parentFragmentManager)
    }

    private fun passwordUpdate() {
        viewModel.passwordUpdateClick(
            binding.passwordUpdate,
            parentFragmentManager,
            requireActivity(),
            requireContext(),
            binding.oldPassword,
            binding.newPassword1,
            binding.newPassword2
        )
    }




}