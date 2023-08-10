package com.furkanmulayim.cardholder.ui.me_future

import android.view.View
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.furkanmulayim.cardholder.data.entity.Cards
import com.furkanmulayim.cardholder.data.repo.CardsDaoRepository
import com.furkanmulayim.cardholder.utils.aes64Decrypted
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MeFragmentViewModel @Inject constructor(private val getTcRepo: CardsDaoRepository) :
    ViewModel() {

    var tcList: MutableLiveData<List<Cards>>


    init {
        getTcList()
        tcList = getTcRepo.tcPostViewModel()
    }

    fun getTcList() {
        getTcRepo.getTc()
    }

    fun updateTcList(newList: List<Cards>) {
        tcList.value = newList
    }

    fun deleteCard(cardsList: Cards) {
        getTcRepo.deleteCard(
            cardsList.cardId,
            cardsList.cardName,
            aes64Decrypted(cardsList.cardNumber.replace(" ", "")),
            cardsList.cardPersonName,
            cardsList.cardMonth,
            cardsList.cardYear,
            cardsList.cardCvv,
            cardBackground = "TcKimlik"
        )
    }


    fun passUpdate(button: Button, view: View, id: Int) {
        button.setOnClickListener {
            Navigation.findNavController(view).navigate(id)
        }
    }


}