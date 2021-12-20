package com.fishtudo.awesomeseries.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fishtudo.awesomeseries.R
import com.fishtudo.awesomeseries.model.CastCredits
import kotlinx.android.synthetic.main.cast_credits_item.view.*

class CastCreditsAdapter(
    private val context: Context,
    private val castCredits: MutableList<CastCredits> = mutableListOf(),
    var onItemClickListener: (castCredits: CastCredits) -> Unit = {}
) : RecyclerView.Adapter<CastCreditsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(
                    R.layout.cast_credits_item,
                    parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(castCredits[position])
    }

    override fun getItemCount(): Int {
        return castCredits.size
    }

    fun updateItems(castCredits: List<CastCredits>) {
        notifyItemRangeRemoved(0, this.castCredits.size)
        this.castCredits.clear()
        this.castCredits.addAll(castCredits)
        notifyItemRangeInserted(0, this.castCredits.size)
    }

    private fun onClickItem(castCredits: CastCredits) {
        onItemClickListener(castCredits)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var castCredits: CastCredits

        init {
            itemView.setOnClickListener {
                if (::castCredits.isInitialized) {
                    onClickItem(castCredits)
                }
            }
        }

        fun setItem(castCredits: CastCredits) {
            this.castCredits = castCredits
            itemView.name.text = castCredits.getUrl()
        }
    }
}