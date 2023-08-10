package com.furkanmulayim.cardholder.ui.detail_future

import android.content.Context
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furkanmulayim.cardholder.R
import com.furkanmulayim.cardholder.data.entity.Cards
import com.furkanmulayim.cardholder.data.repo.CardsDaoRepository
import com.furkanmulayim.cardholder.utils.aes64Decrypted
import com.furkanmulayim.cardholder.utils.aes64Encrypted
import com.furkanmulayim.cardholder.utils.formatWithSpaces
import com.furkanmulayim.cardholder.utils.showMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel @Inject constructor(private val updateCardRepo: CardsDaoRepository) :
    ViewModel() {


    var cardId = MutableLiveData<String>()
    var cardName = MutableLiveData<String>()
    var cardNumber = MutableLiveData<String>()
    var cardNameSurname = MutableLiveData<String>()
    var cardMonth = MutableLiveData<String>()
    var cardYear = MutableLiveData<String>()
    var cardCvv = MutableLiveData<String>()

    private val _isEditTextFocused = MutableLiveData<Boolean>()
    private val isEditTextFocused: LiveData<Boolean> = _isEditTextFocused

    fun updateEditTextFocus(hasFocus: Boolean) {
        _isEditTextFocused.value = hasFocus
        if (isEditTextFocused.value == false) {
            if (cardMonth.value.toString().length < 2) {
                cardMonth.value = "0" + cardMonth.value
            }

            if (cardMonth.value == "0" || cardMonth.value == "00") {
                cardMonth.value = ""
            }
        }
    }

    private fun cardNumberControl(): Boolean {
        return cardNumber.value.toString().replace(
            " ",
            ""
        ).length == 16 && cardMonth.value.toString() != "0" && cardMonth.value.toString() != "00"
    }

    fun update(context: Context, fm: FragmentManager) {
        if (cardNumberControl()) {
            updateCard(context)
            fm.popBackStack()
        } else {

            //burada kaldım buraya bak
            message(context, R.string.tanimsizKayit)
        }
    }

    fun delete(context: Context, fm: FragmentManager) {
        if (cardNumberControl()) {
            deleteCard(context)
            fm.popBackStack()
        }
    }

    private fun updateCard(context: Context) {
        updateCardRepo.updateCard(
            cardId.value.toString(),
            cardName.value.toString(),
            aes64Encrypted(cardNumber.value.toString().replace(" ", "")),
            cardNameSurname.value.toString(),
            cardMonth.value.toString(),
            cardYear.value.toString(),
            aes64Encrypted(cardCvv.value.toString()),
            cardBackground = "Card"
        )
        message(context, R.string.card_updated)
    }


    fun popBackStack(backButton: ImageView, fm: FragmentManager) {
        backButton.setOnClickListener {
            fm.popBackStack()
        }
    }

    private fun deleteCard(context: Context) {
        updateCardRepo.deleteCard(
            cardId.value.toString(),
            cardName.value.toString(),
            aes64Encrypted(cardNumber.value.toString().replace(" ", "")),
            cardNameSurname.value.toString(),
            cardMonth.value.toString(),
            cardYear.value.toString(),
            aes64Encrypted(cardCvv.value.toString()),
            cardBackground = "Card"
        )
        message(context, R.string.card_deleted)
    }

    private fun message(context: Context, resId: Int) {
        context.apply {
            this.getString(resId).let { message ->
                this.showMessage(message)
            }
        }
    }

    fun incomingData(list: Cards) {
        cardId.value = list.cardId
        cardName.value = list.cardName
        cardNumber.value = aes64Decrypted(list.cardNumber).formatWithSpaces()
        cardNameSurname.value = list.cardPersonName
        cardMonth.value = list.cardMonth
        cardYear.value = list.cardYear
        cardCvv.value = aes64Decrypted(list.cardCvv)
    }

    fun cardNumGet(editText: EditText) {
        editText.hint = cardNumber.value.toString()
    }

    fun cardNameOnTextChanged(text: String) {
        cardName.value = text
    }

    fun cardNumberOnTextChanged(text: String) {
        cardNumber.value = text.formatWithSpaces()
    }

    fun cardNameSurnameOnTextChanged(text: String) {
        cardNameSurname.value = text
    }

    fun cardMonthOnTextChanged(text: String) {
        cardMonth.value = text
        if (text.length > 2) {
            cardMonth.value = text.replace("0", "")
        }
    }

    fun cardYearOnTextChanged(text: String) {
        cardYear.value = text
    }

    fun cardCvvOnTextChanged(text: String) {
        cardCvv.value = text
    }


}