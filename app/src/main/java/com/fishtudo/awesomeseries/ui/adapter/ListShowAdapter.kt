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
    private val showList: MutableList<Show> = mutableListOf(),
    var onItemClickListener: (show: Show) -> Unit = {},
    var onFavoriteItemClickListener: (show: Show) -> Unit = {},
    var favoriteVerifier: ((show: Show, callback: (Boolean) -> Unit) -> Unit)? = null
) : RecyclerView.Adapter<ListShowAdapter.ViewHolder>() {

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
            itemView.favorite_star.setOnClickListener {
                if (::show.isInitialized) {
                    onFavoriteItemClickListener(show)
                }
            }
        }

        fun setShowItem(show: Show) {
            this.show = show
            itemView.seasonNumber.text = show.name
            show.image?.let {
                imageUtil.downloadImage(context, it, itemView.image)
            }

            favoriteVerifier?.let {
                it(show) {
                    itemView.favorite_star.setImageResource(findImageResource(it))
                }
            }
        }

        private fun findImageResource(boolean: Boolean) =
            if (boolean) android.R.drawable.star_big_on
            else android.R.drawable.star_big_off
    }
}