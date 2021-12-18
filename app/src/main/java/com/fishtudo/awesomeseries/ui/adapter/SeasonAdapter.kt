package com.fishtudo.awesomeseries.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fishtudo.awesomeseries.R
import com.fishtudo.awesomeseries.model.Episode
import kotlinx.android.synthetic.main.show_item.view.*

class SeasonAdapter(
    private val context: Context,
    private val seasonList: MutableList<Episode> = mutableListOf(),
    var onItemClickListener: (show: Episode) -> Unit = {}
) : RecyclerView.Adapter<SeasonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(
                    R.layout.season_item,
                    parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(seasonList[position])
    }

    override fun getItemCount(): Int {
        return seasonList.size
    }

    fun updateItems(showList: List<Episode>) {
        notifyItemRangeRemoved(0, this.seasonList.size)
        this.seasonList.clear()
        this.seasonList.addAll(showList)
        notifyItemRangeInserted(0, this.seasonList.size)
    }

    private fun onClickItem(episode: Episode) {
        onItemClickListener(episode)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var episode: Episode

        init {
            itemView.setOnClickListener {
                if (::episode.isInitialized) {
                    onClickItem(episode)
                }
            }
        }

        fun setItem(episode: Episode) {
            this.episode = episode
            itemView.seasonNumber.text = episode.url
        }
    }
}