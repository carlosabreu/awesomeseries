package com.fishtudo.awesomeseries.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fishtudo.awesomeseries.R
import com.fishtudo.awesomeseries.model.CastCredits
import com.fishtudo.awesomeseries.model.Show
import kotlinx.android.synthetic.main.cast_credits_item.view.*

class CastCreditsAdapter(
    private val context: Context,
    private val data: MutableList<ShowWrapper> = mutableListOf(),
    var showInfoSolicitor: (showId: List<Int>, callback: (Show) -> Unit) -> Unit = { _, _ -> },
    var onItemClickListener: (show: Show) -> Unit = {}
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
        holder.setItem(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateItems(castCredits: List<CastCredits>) {
        val separateShowIds = separateShowIds(castCredits)
        notifyItemRangeRemoved(0, this.data.size)
        this.data.clear()
        this.data.addAll(separateShowIds.map { ShowWrapper(id = it) })
        requestShows(separateShowIds)
        notifyItemRangeInserted(0, this.data.size)
    }

    private fun requestShows(separateShowIds: List<Int>) {
        showInfoSolicitor(separateShowIds) { show ->
            updateData(show)
        }
    }

    private fun updateData(show: Show) {
        val items = data.filter { it.id == show.id }
        if (items.isNotEmpty()) {
            val index = data.indexOf(items[0])
            notifyItemRemoved(index)
            data[index] = ShowWrapper(show.id, show)
            notifyItemInserted(index)
        }
    }

    private fun separateShowIds(castCredits: List<CastCredits>) =
        castCredits.map { it.getUrl().split("/").last().toInt() }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var showWrapper: ShowWrapper

        init {
            itemView.setOnClickListener {
                if (::showWrapper.isInitialized) {
                    showWrapper.show?.let {
                        onItemClickListener(it)
                    }
                }
            }
        }

        fun setItem(showWrapper: ShowWrapper) {
            this.showWrapper = showWrapper
            if (showWrapper.show != null) {
                itemView.name.text = showWrapper.show.name
                itemView.progressBar.visibility = View.GONE
            } else {
                itemView.name.text = ""
                itemView.progressBar.visibility = View.VISIBLE
            }
        }
    }

    inner class ShowWrapper(val id: Int, val show: Show? = null)
}