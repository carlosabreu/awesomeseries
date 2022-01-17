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

class ListShowAdapter(
    private val context: Context,
    private var showList: MutableList<Show> = mutableListOf(),
    var onItemClickListener: (Show) -> Unit = {},
    var onFavoriteItemClickListener: (Show, Boolean) -> Unit = { _, _ -> },
) : RecyclerView.Adapter<ListShowAdapter.ViewHolder>() {

    private var favoriteList: ArrayList<Show> = ArrayList()

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

    fun addItems(showList: List<Show>) {
        val previousSize = this.showList.size
        this.showList.addAll(showList)
        this.showList = this.showList.toSet().toMutableList()
        notifyItemRangeInserted(previousSize, this.showList.size)
    }

    fun clear() {
        notifyItemRangeRemoved(0, this.showList.size)
        this.showList.clear()
    }

    fun updateFavorites(favoriteList: List<Show>) {
        this.favoriteList.clear()
        this.favoriteList.addAll(favoriteList)
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
            itemView.favorite_star.setOnClickListener {
                if (::show.isInitialized) {
                    val filter = favoriteList.filter { show.id == it.id }
                    if (filter.isEmpty()) {
                        favoriteList.add(show)
                    } else {
                        favoriteList.remove(filter[0])
                    }
                    updateFavoriteStars(show)
                    onFavoriteItemClickListener(show, filter.isEmpty())
                }
            }
        }

        fun setShowItem(show: Show) {
            this.show = show
            itemView.seasonNumber.text = show.name
            show.image?.let {
                imageUtil.downloadImage(context, it, itemView.image)
            }
            updateFavoriteStars(show)
        }

        private fun updateFavoriteStars(show: Show) {
            itemView.favorite_star.setImageResource(
                findImageResource(
                    favoriteList.any { show.id == it.id }
                )
            )
        }

        private fun findImageResource(boolean: Boolean) =
            if (boolean) android.R.drawable.star_big_on
            else android.R.drawable.star_big_off
    }
}