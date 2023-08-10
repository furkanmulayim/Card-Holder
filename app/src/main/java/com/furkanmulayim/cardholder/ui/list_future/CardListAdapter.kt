package com.furkanmulayim.cardholder.ui.list_future

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.furkanmulayim.cardholder.R
import com.furkanmulayim.cardholder.data.entity.Cards
import com.furkanmulayim.cardholder.databinding.ItemCardBinding
import com.furkanmulayim.cardholder.utils.aes64Decrypted
import com.furkanmulayim.cardholder.utils.formatForListing
import com.furkanmulayim.cardholder.utils.formatWithSpaces
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CardListAdapter(var context: Context, var cardList: List<Cards>) :
    RecyclerView.Adapter<CardListAdapter.CardViewHolder>() {

    inner class CardViewHolder(binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ItemCardBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cardList[position]
        val b = holder.binding

        var cn = card.cardNumber

        b.gosterge.text = formatForListing(aes64Decrypted(cn).formatWithSpaces())
        b.textView2.text = card.cardName
        b.textView7.text = aes64Decrypted(cn)
        b.textView3.text = card.cardPersonName
        b.textView6.text = card.cardMonth
        b.textView4.text = card.cardYear

        holder.itemView.setOnClickListener {
            it.isClickable = false
            val sendDataDetail =
                CardListFragmentDirections.actionCardListFragmentToCardDetailsFragment(card)
            it.startAnimation(AnimationUtils.loadAnimation(context, R.anim.recyc_click))
            val coroutineScope = CoroutineScope(Dispatchers.Main)

            coroutineScope.launch {
                delay(200)
                Navigation.findNavController(it).navigate(sendDataDetail)
            }
        }
    }

    override fun getItemCount(): Int {
        return cardList.size
    }


}
