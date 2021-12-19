package com.fishtudo.awesomeseries.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fishtudo.awesomeseries.R
import com.fishtudo.awesomeseries.model.Episode
import com.fishtudo.awesomeseries.model.Season
import com.fishtudo.awesomeseries.model.Show
import com.fishtudo.awesomeseries.repositories.TVMazeRepositoryFactory
import com.fishtudo.awesomeseries.ui.adapter.EpisodeAdapter
import com.fishtudo.awesomeseries.ui.viewmodel.ListEpisodeViewModel
import com.fishtudo.awesomeseries.ui.viewmodel.ListSeasonViewModel
import com.fishtudo.awesomeseries.ui.viewmodel.factory.EpisodeListViewModelFactory
import com.fishtudo.awesomeseries.ui.viewmodel.factory.ShowDetailsViewModelFactory
import com.fishtudo.awesomeseries.util.ImageUtil
import kotlinx.android.synthetic.main.activity_show_details.*

class ShowDetailsActivity : AppCompatActivity() {

    private val imageUtil = ImageUtil()

    private val seasonsViewModel by lazy {
        val repository = TVMazeRepositoryFactory().createRepository()
        val factory = ShowDetailsViewModelFactory(repository)
        ViewModelProvider(this, factory)[ListSeasonViewModel::class.java]
    }

    private val episodesViewModel by lazy {
        val repository = TVMazeRepositoryFactory().createRepository()
        val factory = EpisodeListViewModelFactory(repository)
        ViewModelProvider(this, factory)[ListEpisodeViewModel::class.java]
    }

    private val adapter by lazy {
        EpisodeAdapter(context = this)
    }

    var seasonCache: List<Season>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_details)
        setRecyclerViewUp()
        findShowInIntent()?.let {
            seasonNumber.text = it.name
            days_in_air.text = it.schedule.days.joinToString()
            time_in_air.text = it.schedule.time
            genres.text = it.genres[0]
            summary.text = Html.fromHtml(it.summary, Html.FROM_HTML_MODE_COMPACT)
            it.image?.let { image ->
                imageUtil.downloadImage(this, image, poster)
            }
            requestSeasons(it)
        }
    }

    private fun setRecyclerViewUp() {
        recyclerview.adapter = adapter
        recyclerview.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        adapter.onItemClickListener = this::onItemClicked
    }

    private fun findShowInIntent() =
        intent.getBundleExtra(PARAMETER_BUNDLE)?.getParcelable<Show>(PARAMETER_PARCELABLE_ITEM)

    private fun requestSeasons(it: Show) {
        seasonsViewModel.listSeasons(showId = it.id).observe(this) { resource ->
            resource?.data.let { list ->
                list?.let {
                    configureSpinner(it)
                    seasonCache = it
                    if (it.isNotEmpty()) {
                        requestEpisodeList(it[0])
                    }
                }
            }
        }
    }

    private fun configureSpinner(list: List<Season>) {
        spinner.visibility = View.VISIBLE
        spinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
                list.map {
                    it.number
                })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                l: Long
            ) {
                seasonCache?.let {
                    requestEpisodeList(it[position])
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                return
            }
        }
    }

    private fun requestEpisodeList(season: Season) {
        episodesViewModel.listEpisodeBySeason(season.id).observe(this) { resource ->
            resource?.data.let { list ->
                list?.let {
                    recyclerview.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    adapter.updateItems(it)
                }
            }
        }
    }

    private fun onItemClicked(episode: Episode) {
        val bundle = Bundle()
        bundle.putParcelable(PARAMETER_PARCELABLE_ITEM, episode)
        val intent = Intent(this, EpisodeDetailsActivity::class.java)
        intent.putExtra(PARAMETER_BUNDLE, bundle)
        startActivity(intent)
    }
}

