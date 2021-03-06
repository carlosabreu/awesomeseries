package com.fishtudo.awesomeseries.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fishtudo.awesomeseries.R
import com.fishtudo.awesomeseries.model.Show
import com.fishtudo.awesomeseries.util.ImageUtil
import kotlinx.android.synthetic.main.show_item.view.*

class FavoriteShowAdapter(
    private val context: Context,
    private val showList: MutableList<Show> = mutableListOf(),
    var onItemClickListener: (Show) -> Unit = {},
) : RecyclerView.Adapter<FavoriteShowAdapter.ViewHolder>() {

    private val imageUtil = ImageUtil()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(
                    R.layout.show_item,
                    parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setShowItem(showList[position])
    }

    override fun getItemCount(): Int {
        return showList.size
    }

    fun updateItems(showList: List<Show>) {
        notifyItemRangeRemoved(0, this.showList.size)
        this.showList.clear()
        this.showList.addAll(showList)
        notifyItemRangeInserted(0, this.showList.size)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var show: Show

        init {
            itemView.setOnClickListener {
                if (::show.isInitialized) {
                    onItemClickListener(show)
                }
            }
            itemView.favorite_star.visibility = View.GONE
        }

        fun setShowItem(show: Show) {
            this.show = show
            itemView.seasonNumber.text = show.name
            show.image?.let {
                imageUtil.downloadImage(context, it, itemView.image)
            }
        }
    }
}