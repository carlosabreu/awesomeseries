package com.fishtudo.awesomeseries.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.fishtudo.awesomeseries.R
import com.fishtudo.awesomeseries.model.Show
import com.fishtudo.awesomeseries.repositories.TVMazeRepository
import com.fishtudo.awesomeseries.ui.adapter.ListShowAdapter
import com.fishtudo.awesomeseries.ui.viewmodel.ListShowViewModel
import com.fishtudo.awesomeseries.ui.viewmodel.factory.ListShowViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter by lazy {
        ListShowAdapter(context = this)
    }

    private val viewModel by lazy {
        val repository = TVMazeRepository()
        val factory = ListShowViewModelFactory(repository)
        ViewModelProvider(this, factory)[ListShowViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        setRecyclerViewUp()
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                requestSearchListItems(query)
                return
            }
        }

        requestDefaultListItems()
    }

    private fun requestSearchListItems(query: String) {
        viewModel.searchShowsByPage(query).observe(this) { resource ->
            resource?.data.let { list ->
                list?.let {
                    adapter.updateItems(it)
                }
            }
        }
    }

    private fun setRecyclerViewUp() {
        recyclerview.adapter = adapter
        recyclerview.addItemDecoration(DividerItemDecoration(this, VERTICAL))
        adapter.onItemClickListener = this::onItemClicked
    }

    private fun onItemClicked(show: Show) {
        val bundle = Bundle()
        bundle.putParcelable(PARAMETER_PARCELABLE_ITEM, show)
        val intent = Intent(this, ShowDetailsActivity::class.java)
        intent.putExtra(PARAMETER_BUNDLE, bundle)
        startActivity(intent)
    }

    private fun requestDefaultListItems() {
        viewModel.listShowsByPage(0).observe(this) { resource ->
            resource?.data.let { list ->
                list?.let {
                    adapter.updateItems(it)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        return true
    }
}