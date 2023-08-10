package com.furkanmulayim.cardholder.ui.add_future

import android.app.Activity
import android.content.Context
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furkanmulayim.cardholder.R
import com.furkanmulayim.cardholder.data.repo.CardsDaoRepository
import com.furkanmulayim.cardholder.utils.aes64Encrypted
import com.furkanmulayim.cardholder.utils.formatWithSpaces
import com.furkanmulayim.cardholder.utils.hideKeyboard
import com.furkanmulayim.cardholder.utils.showMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class AddCardFragmentViewModel @Inject constructor(private val saveCardRepo: CardsDaoRepository) :
    ViewModel() {

    val rotateLayoutLiveData = MutableLiveData<Boolean>()

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

    fun rotateLayout() {
        val currentValue = rotateLayoutLiveData.value ?: false
        rotateLayoutLiveData.value = !currentValue
    }

    private fun saveCard(activity: Activity) {
        cardNumber.value = cardNumber.value.toString().replace(" ", "")
        saveCardRepo.saveCard(
            cardId = randomDeger(),
            cardName.value.toString(),
            aes64Encrypted(cardNumber.value.toString()),
            cardNameSurname.value.toString(),
            cardMonth.value.toString(),
            cardYear.value.toString(),
            aes64Encrypted(cardCvv.value.toString()),
            "Card"
        )
        hideKeyboard(activity)
    }

    private fun randomDeger(): String {
        return Random(999999).toString() + " "
    }

    private fun hideKeyboard(activity: Activity) {
        activity.hideKeyboard()
    }

    fun saveControl(context: Context, activity: Activity): Boolean {
        var a: Boolean
        a = if (!cardName.value.isNullOrEmpty() && !cardNumber.value.isNullOrEmpty() && !cardNameSurname.value.isNullOrEmpty() && !cardMonth.value.isNullOrEmpty() && !cardYear.value.isNullOrEmpty() && !cardCvv.value.isNullOrEmpty()) {
            if (cardNumber.value.toString().replace(" ","").length > 15) {
                saveCard(activity)
                message(context, R.string.new_card_added)
                true
            } else {
                message(context, R.string.new_card_not_added2)
                false
            }

        } else {
            message(context, R.string.new_card_not_added)
            false
        }
        return a
    }

    private fun message(context: Context, resId: Int) {
        context.apply {
            this.getString(resId).let { message ->
                this.showMessage(message)
            }
        }
    }

    fun cleanEditText(textList: List<TextView>) {
        textList[0].text = ""
        textList[1].text = ""
        textList[2].text = ""
        textList[3].text = ""
        textList[4].text = ""
        textList[5].text = ""

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

