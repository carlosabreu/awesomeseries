package com.fishtudo.awesomeseries.ui.activities

import android.os.Bundle
import android.text.Html
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.fishtudo.awesomeseries.R
import com.fishtudo.awesomeseries.model.Season
import com.fishtudo.awesomeseries.model.Show
import com.fishtudo.awesomeseries.repositories.TVMazeRepository
import com.fishtudo.awesomeseries.ui.adapter.SeasonAdapter
import com.fishtudo.awesomeseries.ui.viewmodel.ListEpisodeViewModel
import com.fishtudo.awesomeseries.ui.viewmodel.ListSeasonViewModel
import com.fishtudo.awesomeseries.ui.viewmodel.factory.EpisodeListViewModelFactory
import com.fishtudo.awesomeseries.ui.viewmodel.factory.ShowDetailsViewModelFactory
import com.fishtudo.awesomeseries.util.ImageUtil
import kotlinx.android.synthetic.main.activity_show_details.*


class ShowDetailsActivity : AppCompatActivity() {

    private val imageUtil = ImageUtil()

    private val seasonsViewModel by lazy {
        val repository = TVMazeRepository()
        val factory = ShowDetailsViewModelFactory(repository)
        ViewModelProvider(this, factory)[ListSeasonViewModel::class.java]
    }

    private val episodesViewModel by lazy {
        val repository = TVMazeRepository()
        val factory = EpisodeListViewModelFactory(repository)
        ViewModelProvider(this, factory)[ListEpisodeViewModel::class.java]
    }

    private val adapter by lazy {
        SeasonAdapter(context = this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_details)
        findShowInIntent()?.let {
            seasonNumber.text = it.name
            days_in_air.text = it.schedule.days.joinToString()
            time_in_air.text = it.schedule.time
            genres.text = it.genres[0]
            summary.text = Html.fromHtml(it.summary, Html.FROM_HTML_MODE_COMPACT)
            imageUtil.downloadImage(this, it.image, poster)
            requestSeasons(it)
        }
    }

    private fun configureSpinner(list: List<Season>) {
        spinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
                list.map {
                    it.number
                })
    }

    private fun requestSeasons(it: Show) {
        seasonsViewModel.listSeasons(showId = it.id).observe(this) { resource ->
            resource?.data.let { list ->
                list?.let {
                    configureSpinner(it)
                    if (it.isNotEmpty()) {
                        requestEpisodeList(it[0])
                    }
                }
            }
        }
    }

    private fun requestEpisodeList(show: Season) {
        episodesViewModel.listEpisodeBySeason(show.id).observe(this) { resource ->
//            adapter.updateItems(resource.data)
            Toast.makeText(this, "Baixou", Toast.LENGTH_SHORT).show()
        }
    }

    private fun findShowInIntent() =
        intent.getBundleExtra(PARAMETER_BUNDLE)?.getParcelable<Show>(PARAMETER_PARCELABLE_ITEM)
}

