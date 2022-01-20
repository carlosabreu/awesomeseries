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
import com.fishtudo.awesomeseries.model.People
import com.fishtudo.awesomeseries.repositories.Resource
import com.fishtudo.awesomeseries.repositories.factories.TVMazeRepositoryFactory
import com.fishtudo.awesomeseries.ui.adapter.PeopleAdapter
import com.fishtudo.awesomeseries.ui.viewmodel.ListPeopleViewModel
import com.fishtudo.awesomeseries.ui.viewmodel.factory.ListPeopleViewModelFactory
import kotlinx.android.synthetic.main.activity_people_search.*

class PeopleSearchActivity : AppCompatActivity() {

    private val adapter by lazy {
        PeopleAdapter(context = this)
    }

    private val listPeopleViewModel by lazy {
        val repository = TVMazeRepositoryFactory().createRepository()
        val factory = ListPeopleViewModelFactory(repository)
        ViewModelProvider(this, factory)[ListPeopleViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_search)
        requestPeopleList()
        setRecyclerViewUp()
    }

    private fun setRecyclerViewUp() {
        recyclerview.adapter = adapter
        recyclerview.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        adapter.onItemClickListener = this::onItemClicked
    }

    private fun onItemClicked(people: People) {
        val bundle = Bundle()
        bundle.putParcelable(PARAMETER_PARCELABLE_ITEM, people)
        val intent = Intent(this, CastCreditsActivity::class.java)
        intent.putExtra(PARAMETER_BUNDLE, bundle)
        startActivity(intent)
    }

    private fun requestPeopleList() {
        listPeopleViewModel.listPeople().observe(this) { resource ->
            onResultReceived(resource)
        }
    }

    private fun onResultReceived(resource: Resource<List<People>>) {
        progressBar.visibility = View.GONE
        if (resource.error != null) {
            showError(resource.error)
            return
        }

        resource.data?.let {
            adapter.updateItems(it)
        }
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}