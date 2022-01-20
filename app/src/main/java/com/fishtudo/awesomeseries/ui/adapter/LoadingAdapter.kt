package com.fishtudo.awesomeseries.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fishtudo.awesomeseries.R

class LoadingAdapter(
    private val context: Context,
) : RecyclerView.Adapter<LoadingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadingAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(
                    R.layout.loading_adapter,
                    parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemCount() = 1

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)
}
