package com.furkanmulayim.cardholder.ui.tc_future

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.furkanmulayim.cardholder.R
import com.furkanmulayim.cardholder.databinding.FragmentDriverLicenceBinding
import com.furkanmulayim.cardholder.utils.listenToEditTextsExt2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DriverLicenceFragment : Fragment() {

    private lateinit var binding:FragmentDriverLicenceBinding
    private lateinit var viewModel: DriverLicenceFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_driver_licence, container,false)
        viewModel = ViewModelProvider(this)[DriverLicenceFragmentViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenToEditTexts()
        tcAdd()
        viewModel.popBackStack(binding.backTextButton,parentFragmentManager)
    }

    private fun listenToEditTexts() {
        val textList = listOf(
            binding.textGiveDate,
            binding.textDateEnd,
            binding.textProvince,
            binding.textDriverNo,
        )
        listenToEditTextsExt2(viewModel, textList)
    }

    private fun tcAdd(){
        binding.addCardButton.setOnClickListener {
            viewModel.saveControl(requireContext(),requireView())
        }
    }

}