package com.furkanmulayim.cardholder.ui.me_future

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.furkanmulayim.cardholder.R
import com.furkanmulayim.cardholder.data.entity.Cards
import com.furkanmulayim.cardholder.databinding.ItemTcBinding
import com.furkanmulayim.cardholder.utils.aes64Decrypted
import com.furkanmulayim.cardholder.utils.showDeleteConfirmationDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TcListAdapter(
    var context: Context, var cardList: List<Cards>, var viewModel: MeFragmentViewModel
) : RecyclerView.Adapter<TcListAdapter.CardViewHolder>() {
    inner class CardViewHolder(binding: ItemTcBinding) : RecyclerView.ViewHolder(binding.root) {

        var binding: ItemTcBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ItemTcBinding.inflate(LayoutInflater.from(context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cardList[position]
        val b = holder.binding

        b.textScNo.text = card.cardName
        b.textScAd.text = aes64Decrypted(card.cardNumber)
        b.textScBirthDate.text = card.cardPersonName
        b.textScSerialNo.text = card.cardMonth

        holder.itemView.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(context, R.anim.recyc_click))
            val coroutineScope = CoroutineScope(Dispatchers.Main)
            coroutineScope.launch {
                delay(180)

                context.showDeleteConfirmationDialog(cardList[position].cardName) {
                    viewModel.deleteCard(cardList[position])
                    removeItem(position)
                    viewModelDataChanged(cardList)
                }
            }
        }
    }


    private fun viewModelDataChanged(cardList: List<Cards>) {
        viewModel.updateTcList(cardList)
    }

    private fun removeItem(position: Int) {
        val updatedList = cardList.toMutableList()
        updatedList.removeAt(position)
        cardList = updatedList.toList()
        notifyItemRemoved(position)

    }

    override fun getItemCount(): Int {
        return cardList.size
    }


}
