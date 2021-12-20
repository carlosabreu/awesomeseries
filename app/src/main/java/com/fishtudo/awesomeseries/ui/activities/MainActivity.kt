package com.fishtudo.awesomeseries.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.fishtudo.awesomeseries.R
import com.fishtudo.awesomeseries.model.Session
import com.fishtudo.awesomeseries.model.Show
import com.fishtudo.awesomeseries.repositories.FavoriteShowRepository
import com.fishtudo.awesomeseries.repositories.PinRepository
import com.fishtudo.awesomeseries.repositories.Resource
import com.fishtudo.awesomeseries.repositories.factories.TVMazeRepositoryFactory
import com.fishtudo.awesomeseries.ui.adapter.ListShowAdapter
import com.fishtudo.awesomeseries.ui.viewmodel.ListFavoriteViewModel
import com.fishtudo.awesomeseries.ui.viewmodel.ListShowViewModel
import com.fishtudo.awesomeseries.ui.viewmodel.factory.ListFavoriteViewModelFactory
import com.fishtudo.awesomeseries.ui.viewmodel.factory.ListShowViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var searchView: SearchView? = null

    private val adapter by lazy {
        ListShowAdapter(context = this)
    }

    private val listShowViewModel by lazy {
        val repository = TVMazeRepositoryFactory().createRepository()
        val factory = ListShowViewModelFactory(repository)
        ViewModelProvider(this, factory)[ListShowViewModel::class.java]
    }

    private val favoritesViewModel by lazy {
        val repository = FavoriteShowRepository()
        val factory = ListFavoriteViewModelFactory(repository)
        ViewModelProvider(this, factory)[ListFavoriteViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (stillLocked()) {
            startUnlockActivity()
            finish()
        }
        setContentView(R.layout.activity_main)
        setRecyclerViewUp()
        handleIntent(intent)
        loadFavorites()
    }

    private fun startUnlockActivity() {
        val intent = Intent(this, UnlockActivity::class.java)
        startActivity(intent)
    }

    private fun stillLocked(): Boolean {
        val logged = Session.logged
        val userSetPassword = PinRepository().readPin(this) != ""
        return !logged && userSetPassword
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setRecyclerViewUp()
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                displaySearchInformation(query)
                requestSearchListItems(query)
                return
            }
        }

        hideSearchInformation()
        requestDefaultListItems()
    }

    private fun displaySearchInformation(query: String) {
        val searchingBy = "Searching by $query"
        textView.text = searchingBy
        textView.visibility = View.VISIBLE
        close_search.visibility = View.VISIBLE
        close_search.setOnClickListener {
            cancelSearch()
        }
    }

    private fun hideSearchInformation() {
        textView.visibility = View.GONE
        close_search.visibility = View.GONE
    }

    private fun requestSearchListItems(query: String) {
        listShowViewModel.searchShowsByPage(query).observe(this) { resource ->
            onResultReceived(resource)
        }
    }

    private fun onResultReceived(resource: Resource<List<Show>>) {
        progressBar.visibility = View.GONE
        if (resource.error != null) {
            showError(resource.error)
            return
        }

        resource.data?.let {
            adapter.updateItems(it)
        }
    }

    private fun onFavoritesUpdate(resource: Resource<List<Show>>) {
        if (resource.error != null) {
            showError(resource.error)
            return
        }

        resource.data?.let {
            adapter.updateFavorites(it)
        }
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    private fun setRecyclerViewUp() {
        recyclerview.adapter = adapter
        recyclerview.addItemDecoration(DividerItemDecoration(this, VERTICAL))
        adapter.onItemClickListener = this::onItemClicked
        adapter.onFavoriteItemClickListener = this::onFavoriteItemClickListener
    }

    private fun onItemClicked(show: Show) {
        val bundle = Bundle()
        bundle.putParcelable(PARAMETER_PARCELABLE_ITEM, show)
        val intent = Intent(this, ShowDetailsActivity::class.java)
        intent.putExtra(PARAMETER_BUNDLE, bundle)
        startActivity(intent)
    }

    private fun requestDefaultListItems() {
        listShowViewModel.listShowsByPage(0).observe(this) { resource ->
            onResultReceived(resource)
        }
    }

    private fun cancelSearch() {
        searchView?.onActionViewCollapsed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun loadFavorites() {
        favoritesViewModel.listFavorites(this).observe(this) { resource ->
            onFavoritesUpdate(resource)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView?.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.create_pin -> {
                startCreatePinActivity()
                return true
            }
            R.id.favorite -> {
                startFavoriteListActivity()
                return true
            }
            R.id.people_search -> {
                startPeopleSearchActivity()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun onFavoriteItemClickListener(show: Show, inserted: Boolean) {
        val message = if (inserted) "added to" else "removed from"
        Toast.makeText(this, "${show.name} $message favorites", Toast.LENGTH_SHORT).show()
        favoritesViewModel.changeFavoritesStatus(this, show)
    }

    private fun startCreatePinActivity() {
        val intent = Intent(this, CreateAPinActivity::class.java)
        startActivity(intent)
    }

    private fun startFavoriteListActivity() {
        val intent = Intent(this, FavoriteListActivity::class.java)
        startActivity(intent)
    }

    private fun startPeopleSearchActivity() {
        val intent = Intent(this, PeopleSearchActivity::class.java)
        startActivity(intent)
    }
}