package com.fishtudo.awesomeseries.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fishtudo.awesomeseries.R
import com.fishtudo.awesomeseries.model.Show
import com.fishtudo.awesomeseries.repositories.FavoriteShowRepository
import com.fishtudo.awesomeseries.repositories.Resource
import com.fishtudo.awesomeseries.ui.adapter.FavoriteShowAdapter
import com.fishtudo.awesomeseries.ui.viewmodel.ListFavoriteViewModel
import com.fishtudo.awesomeseries.ui.viewmodel.factory.ListFavoriteViewModelFactory
import kotlinx.android.synthetic.main.activity_favorite_list.*
import kotlinx.android.synthetic.main.activity_main.recyclerview

class FavoriteListActivity : AppCompatActivity() {

    private val adapter by lazy {
        FavoriteShowAdapter(context = this)
    }

    private val favoritesViewModel by lazy {
        val repository = FavoriteShowRepository()
        val factory = ListFavoriteViewModelFactory(repository)
        ViewModelProvider(this, factory)[ListFavoriteViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_list)
        setRecyclerViewUp()
        requestFavoriteListItems()
    }

    private fun setRecyclerViewUp() {
        recyclerview.adapter = adapter
        recyclerview.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        adapter.onItemClickListener = this::onItemClicked
    }

    private fun onItemClicked(show: Show) {
        val bundle = Bundle()
        bundle.putParcelable(PARAMETER_PARCELABLE_ITEM, show)
        val intent = Intent(this, ShowDetailsActivity::class.java)
        intent.putExtra(PARAMETER_BUNDLE, bundle)
        startActivity(intent)
    }

    private fun requestFavoriteListItems() {
        favoritesViewModel.listFavorites(this).observe(this) { resource ->
            onResultReceived(resource)
        }
    }

    private fun onResultReceived(resource: Resource<List<Show>>) {
        progressBar.visibility = View.GONE
        if (resource.error != null) {
            showError(resource.error)
            return
        }

        resource.data?.let { unsortedList ->
            if (unsortedList.isEmpty()) {
                no_favorites.visibility = View.VISIBLE
            }
            adapter.updateItems(unsortedList.sortedBy { it.name })
        }
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}