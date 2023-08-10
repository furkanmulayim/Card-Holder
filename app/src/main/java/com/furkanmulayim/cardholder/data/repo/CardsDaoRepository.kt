package com.furkanmulayim.cardholder.data.repo

import androidx.lifecycle.MutableLiveData
import com.furkanmulayim.cardholder.data.entity.Cards
import com.furkanmulayim.cardholder.room.CardsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardsDaoRepository(var cdao: CardsDao) {

    private var cardsList: MutableLiveData<List<Cards>> = MutableLiveData()
    private var tcList: MutableLiveData<List<Cards>> = MutableLiveData()


    fun cardsPostViewModel(): MutableLiveData<List<Cards>> {
        return cardsList
    }

    fun tcPostViewModel(): MutableLiveData<List<Cards>> {
        return tcList
    }

    fun saveCard(
        cardId: String,
        cardName: String,
        cardNumber: String,
        cardNameSurname: String,
        cardMonth: String,
        cardYear: String,
        cardCvv: String,
        cardBackground: String
    ) {

        val job = CoroutineScope(Dispatchers.Main).launch {
            val newCard = Cards(
                cardId = cardId,
                cardName = cardName,
                cardNumber = cardNumber,
                cardPersonName = cardNameSurname,
                cardMonth = cardMonth,
                cardYear = cardYear,
                cardCvv = cardCvv,
                cardImage = cardBackground
            )
            cdao.addCard(newCard)
        }

    }

    fun updateCard(
        cardId:String,
        cardName: String,
        cardNumber: String,
        cardNameSurname: String,
        cardMonth: String,
        cardYear: String,
        cardCvv: String,
        cardBackground: String
    ) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            val updateCard = Cards(
                cardId = cardId,
                cardName = cardName,
                cardNumber = cardNumber,
                cardPersonName = cardNameSurname,
                cardMonth = cardMonth,
                cardYear = cardYear,
                cardCvv = cardCvv,
                cardImage = cardBackground
            )
            cdao.updateCard(updateCard)
        }
    }

    fun deleteCard(
        cardId:String,
        cardName: String,
        cardNumber: String,
        cardNameSurname: String,
        cardMonth: String,
        cardYear: String,
        cardCvv: String,
        cardBackground: String) {

        val job = CoroutineScope(Dispatchers.Main).launch {
            val delete = Cards(
                cardId = cardId,
                cardName = cardName,
                cardNumber = cardNumber,
                cardPersonName = cardNameSurname,
                cardMonth = cardMonth,
                cardYear = cardYear,
                cardCvv = cardCvv,
                cardImage = cardBackground
            )
            cdao.deleteCard(delete)
        }
    }

    fun getAllCards() {
        val job = CoroutineScope(Dispatchers.Main).launch {
            cardsList.value = cdao.allCards("Card")
        }
    }

    fun getTc() {
        val job = CoroutineScope(Dispatchers.Main).launch {
            tcList.value = cdao.tcGet("TcKimlik")
        }
    }
}