package com.furkanmulayim.cardholder.ui.add_future

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.furkanmulayim.cardholder.R
import com.furkanmulayim.cardholder.databinding.FragmentAddCardBinding
import com.furkanmulayim.cardholder.utils.listenToEditTextsExt
import com.furkanmulayim.cardholder.utils.setupCardRotation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCardFragment : Fragment() {

    private lateinit var binding: FragmentAddCardBinding
    private lateinit var viewModel: AddCardFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_card, container, false)

        viewModel = ViewModelProvider(this)[AddCardFragmentViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenToEditTexts()
        rotateCard()
        saveCard()
        editTextMonth()
    }

    private fun rotateCard() {
        binding.cardBackground.setupCardRotation(
            viewModel,
            viewLifecycleOwner,
            binding.constraintLayout,
            binding.textCvv,
            binding.textView29
        )
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
        listenToEditTextsExt(viewModel, liste())
    }

    private fun cleanToTexts() {
        viewModel.cleanEditText(liste())
    }

    private fun saveCard() {
        binding.addCardButton.setOnClickListener {
            var isCardSaved = binding.viewModel?.saveControl(requireContext(), requireActivity())
            if (isCardSaved == true) {
                cleanToTexts()
            }
        }
    }

    private fun editTextMonth() {
        binding.textAddCardMonth.setOnFocusChangeListener { _, hasFocus ->
            viewModel.updateEditTextFocus(hasFocus)
        }
    }
}