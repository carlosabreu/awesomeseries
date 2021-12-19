package com.fishtudo.awesomeseries.repositories.database.entity

import androidx.room.Entity
import com.fishtudo.awesomeseries.model.Show
import java.io.Serializable

@Entity
class ShowEntity : Serializable {

    var movieId = 0

    companion object {
        fun createShowEntityFromShow(show: Show): ShowEntity = ShowEntity().apply {
            movieId = show.id
        }
    }

    override fun toString(): String {
        return "$movieId"
    }
}