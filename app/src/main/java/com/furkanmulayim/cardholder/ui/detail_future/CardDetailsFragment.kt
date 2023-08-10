package com.furkanmulayim.cardholder.ui.detail_future

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.furkanmulayim.cardholder.R
import com.furkanmulayim.cardholder.data.entity.Cards
import com.furkanmulayim.cardholder.databinding.FragmentCardDetailsBinding
import com.furkanmulayim.cardholder.utils.listenToEditTextsExt1
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCardDetailsBinding
    private lateinit var viewModel: DetailFragmentViewModel
    private var isUpdateOn: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_card_details, container, false)

        viewModel = ViewModelProvider(this)[DetailFragmentViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        incomingDataToViewModel()
        listenToEditTexts()
        updateModeOn()
        deleteCard()
        editTextMonth()

        viewModel.popBackStack(binding.backTextButton, parentFragmentManager)
    }

    private fun liste(): List<EditText> {
        return listOf(
            binding.textAddCardName,
            binding.textAddCardNumber,
            binding.textAddCardNameAndSurname,
            binding.textAddCardMonth,
            binding.textAddCardYear,
            binding.textAddCardCvvCode
        )
    }

    private fun listenToEditTexts() {
        listenToEditTextsExt1(viewModel, liste())
    }

    private fun incomingDataToViewModel() {
        val bundle: CardDetailsFragmentArgs by navArgs()
        val list: Cards = bundle.card
        viewModel.incomingData(list)
    }

    private fun updateModeOn() {
        binding.updateCard.setOnClickListener {
            if (isUpdateOn) {
                viewModel.update(requireContext(), parentFragmentManager)
            } else {
                viewModel.cardNumGet(binding.textAddCardNumber)
                binding.updateMode.visibility = View.VISIBLE
                isUpdateOn = true
            }
        }
    }

    private fun deleteCard() {
        binding.deleteCard.setOnClickListener {
            viewModel.delete(requireContext(), parentFragmentManager)
        }
    }

    private fun editTextMonth() {
        binding.textAddCardMonth.setOnFocusChangeListener { _, hasFocus ->
            viewModel.updateEditTextFocus(hasFocus)
        }
    }


}