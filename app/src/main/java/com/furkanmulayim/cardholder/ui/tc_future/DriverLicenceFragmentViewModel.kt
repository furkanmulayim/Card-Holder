package com.furkanmulayim.cardholder.ui.tc_future

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.furkanmulayim.cardholder.R
import com.furkanmulayim.cardholder.data.repo.CardsDaoRepository
import com.furkanmulayim.cardholder.utils.aes64Encrypted
import com.furkanmulayim.cardholder.utils.showMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class DriverLicenceFragmentViewModel @Inject constructor(private val saveCardRepo: CardsDaoRepository) :
    ViewModel() {


    var cardGiveDate = MutableLiveData<String>()
    var cardDateEnd = MutableLiveData<String>()
    var cardProvince = MutableLiveData<String>()
    var cardLicenceNo = MutableLiveData<String>()
    var cardId = MutableLiveData<String>()


    private fun saveCard() {
        saveCardRepo.saveCard(
            cardId = randomDeger(),
            cardName = cardGiveDate.value.toString(),
            cardNumber = aes64Encrypted(cardDateEnd.value.toString()),
            cardNameSurname = cardProvince.value.toString(),
            cardMonth = cardLicenceNo.value.toString(),
            cardYear = "",
            cardCvv = "",
            cardBackground = "TcKimlik"
        )
    }

    private fun randomDeger(): String {
        return Random(999999).toString() + " "
    }

    fun saveControl(context: Context, view: View) {
        if (!cardGiveDate.value.isNullOrEmpty() && !cardDateEnd.value.isNullOrEmpty() && !cardProvince.value.isNullOrEmpty() && !cardLicenceNo.value.isNullOrEmpty()) {
            if (cardGiveDate.value!!.length > 10 && cardDateEnd.value!!.length > 7 && cardProvince.value!!.length>7 && cardLicenceNo.value!!.length>7 ){
                saveCard()
                message(context, R.string.new_tc_added)
                Navigation.findNavController(view)
                    .navigate(R.id.action_driverLicenceFragment_to_meFragment)
            }
            else{
                message(context, R.string.notAddedTc)
            }

        } else {
            message(context, R.string.new_card_not_added)
        }
    }

    private fun message(context: Context, resId: Int) {
        context.apply {
            this.getString(resId).let { message ->
                this.showMessage(message)
            }
        }
    }

    fun popBackStack(backButton: ImageView, fm: FragmentManager) {
        backButton.setOnClickListener {
            fm.popBackStack()
        }
    }

    fun cardGiveDateTextChanged(text: String) {
        cardGiveDate.value = text
    }

    fun cardDateEndTextChanged(text: String) {
        cardDateEnd.value = text
    }

    fun cardLicenceNoTextChanged(text: String) {
        cardProvince.value = text
    }

    fun cardYearOnTextChanged(text: String) {
        cardLicenceNo.value = text
    }

}