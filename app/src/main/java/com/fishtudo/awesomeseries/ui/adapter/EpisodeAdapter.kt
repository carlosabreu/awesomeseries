package com.fishtudo.awesomeseries.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fishtudo.awesomeseries.R
import com.fishtudo.awesomeseries.model.Episode
import kotlinx.android.synthetic.main.episode_item.view.*

class EpisodeAdapter(
    private val context: Context,
    private val episodeList: MutableList<Episode> = mutableListOf(),
    var onItemClickListener: (show: Episode) -> Unit = {}
) : RecyclerView.Adapter<EpisodeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(
                    R.layout.episode_item,
                    parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(episodeList[position])
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }

    fun updateItems(showList: List<Episode>) {
        notifyItemRangeRemoved(0, this.episodeList.size)
        this.episodeList.clear()
        this.episodeList.addAll(showList)
        notifyItemRangeInserted(0, this.episodeList.size)
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
            "${episode.number} - ${episode.name}".also { itemView.description.text = it }
        }
    }
}