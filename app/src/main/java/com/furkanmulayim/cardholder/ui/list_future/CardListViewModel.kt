package com.furkanmulayim.cardholder.ui.list_future

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furkanmulayim.cardholder.data.entity.Cards
import com.furkanmulayim.cardholder.data.repo.CardsDaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CardListViewModel @Inject constructor(private val allCardRepo: CardsDaoRepository) :
    ViewModel() {

    var cardList: MutableLiveData<List<Cards>>

    init {
        getCardList()
        cardList = allCardRepo.cardsPostViewModel()
    }

    fun getCardList() {
        allCardRepo.getAllCards()
    }

}