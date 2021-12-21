package com.fishtudo.awesomeseries.ui.activities

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.fishtudo.awesomeseries.R
import com.fishtudo.awesomeseries.model.Episode
import com.fishtudo.awesomeseries.util.ImageUtil
import kotlinx.android.synthetic.main.activity_episode_details.*

class EpisodeDetailsActivity : AppCompatActivity() {

    private val imageUtil = ImageUtil()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode_details)
        findEpisodeInIntent()?.let { episode ->
            "${episode.number}. ${episode.name}".also { name.text = it }
            "Season ${episode.season}".also { season.text = it }
            summary.text = Html.fromHtml(episode.summary, Html.FROM_HTML_MODE_COMPACT)
            episode.image?.let { imageUtil.downloadImage(this, it, poster) }
        }
    }

    private fun findEpisodeInIntent() =
        intent.getBundleExtra(PARAMETER_BUNDLE)?.getParcelable<Episode>(PARAMETER_PARCELABLE_ITEM)
}