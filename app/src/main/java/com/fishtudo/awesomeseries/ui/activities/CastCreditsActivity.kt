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
import com.fishtudo.awesomeseries.model.CastCredits
import com.fishtudo.awesomeseries.model.People
import com.fishtudo.awesomeseries.model.Show
import com.fishtudo.awesomeseries.repositories.Resource
import com.fishtudo.awesomeseries.repositories.factories.TVMazeRepositoryFactory
import com.fishtudo.awesomeseries.ui.adapter.CastCreditsAdapter
import com.fishtudo.awesomeseries.ui.viewmodel.ListCastCreditsViewModel
import com.fishtudo.awesomeseries.ui.viewmodel.ListShowViewModel
import com.fishtudo.awesomeseries.ui.viewmodel.factory.ListCastCreditsViewModelFactory
import com.fishtudo.awesomeseries.ui.viewmodel.factory.ListShowViewModelFactory
import com.fishtudo.awesomeseries.util.ImageUtil
import kotlinx.android.synthetic.main.activity_cast_credits.*

class CastCreditsActivity : AppCompatActivity() {

    private val imageUtil = ImageUtil()

    private val castCreditsViewModel by lazy {
        val repository = TVMazeRepositoryFactory().createRepository()
        val factory = ListCastCreditsViewModelFactory(repository)
        ViewModelProvider(this, factory)[ListCastCreditsViewModel::class.java]
    }

    private val adapter by lazy {
        CastCreditsAdapter(context = this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cast_credits)
        requestCastCreditsList()
        setRecyclerViewUp()
        findShowInIntent()?.let {
            name.text = it.name
            imageUtil.downloadImage(this, it.image, image)
        }
    }

    private fun setRecyclerViewUp() {
        recyclerview.adapter = adapter
        recyclerview.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        adapter.onItemClickListener = this::onItemClicked
        adapter.showInfoSolicitor = this::requestSingleShow
    }

    private fun requestSingleShow(id: List<Int>, callback: (Show) -> Unit) {
        val viewModel = createListShowViewModel()
        viewModel.requireShowById(id).observe(this) { resource ->
            resource.data?.let { show -> callback(show) }
        }
    }

    private fun onItemClicked(show: Show) {
        val bundle = Bundle()
        bundle.putParcelable(PARAMETER_PARCELABLE_ITEM, show)
        val intent = Intent(this, ShowDetailsActivity::class.java)
        intent.putExtra(PARAMETER_BUNDLE, bundle)
        startActivity(intent)
    }

    private fun findShowInIntent() =
        intent.getBundleExtra(PARAMETER_BUNDLE)?.getParcelable<People>(PARAMETER_PARCELABLE_ITEM)

    private fun requestCastCreditsList() {
        castCreditsViewModel.listCastCredits(1).observe(this) { resource ->
            onResultReceived(resource)
        }
    }

    private fun onResultReceived(resource: Resource<List<CastCredits>>) {
        progressBar.visibility = View.GONE
        if (resource.error != null) {
            showError(resource.error)
            return
        }

        resource.data?.let {
            adapter.updateItems(it)
        }
    }

    private fun createListShowViewModel(): ListShowViewModel {
        val repository = TVMazeRepositoryFactory().createRepository()
        val factory = ListShowViewModelFactory(repository)
        return ViewModelProvider(this, factory)[ListShowViewModel::class.java]
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}


