package com.furkanmulayim.cardholder.ui.lock_future

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.furkanmulayim.cardholder.R

class NumberAdapter(
    private val items: String,
    private val onclick: (String) -> Unit
) :
    RecyclerView.Adapter<NumberAdapter.NumberHolder>() {

    class NumberHolder(var view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_list, parent, false)
        return NumberHolder(view)
    }

    override fun onBindViewHolder(holder: NumberHolder, position: Int) {
        val itemSayi = holder.itemView.findViewById<Button>(R.id.button1)
        val item = items.substring(position, position + 1)
        itemSayi.text = item

        itemSayi.setOnClickListener {
            onclick.invoke(item)

            val anim = AnimationUtils.loadAnimation(
                holder.itemView.context,
                androidx.appcompat.R.anim.abc_grow_fade_in_from_bottom
            )
            itemSayi.startAnimation(anim)
        }
    }

    override fun getItemCount(): Int {
        return items.length
    }
}