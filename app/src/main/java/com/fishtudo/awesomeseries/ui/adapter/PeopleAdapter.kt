package com.fishtudo.awesomeseries.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fishtudo.awesomeseries.R
import com.fishtudo.awesomeseries.model.People
import com.fishtudo.awesomeseries.util.ImageUtil
import kotlinx.android.synthetic.main.activity_episode_details.view.*
import kotlinx.android.synthetic.main.activity_episode_details.view.name
import kotlinx.android.synthetic.main.people_item.view.*

class PeopleAdapter(
    private val context: Context,
    private val peopleList: MutableList<People> = mutableListOf(),
    var onItemClickListener: (show: People) -> Unit = {}
) : RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {

    private val imageUtil = ImageUtil()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(
                    R.layout.people_item,
                    parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(peopleList[position])
    }

    override fun getItemCount(): Int {
        return peopleList.size
    }

    fun updateItems(showList: List<People>) {
        notifyItemRangeRemoved(0, this.peopleList.size)
        this.peopleList.clear()
        this.peopleList.addAll(showList)
        notifyItemRangeInserted(0, this.peopleList.size)
    }

    private fun onClickItem(people: People) {
        onItemClickListener(people)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var people: People

        init {
            itemView.setOnClickListener {
                if (::people.isInitialized) {
                    onClickItem(people)
                }
            }
        }

        fun setItem(people: People) {
            this.people = people
            itemView.name.text = people.name
            imageUtil.downloadImage(context, people.image, itemView.image)
        }
    }
}