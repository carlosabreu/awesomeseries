package com.fishtudo.awesomeseries.repositories.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fishtudo.awesomeseries.model.Schedule
import com.fishtudo.awesomeseries.model.Show
import java.io.Serializable

@Entity
class ShowEntity : Serializable {

    @PrimaryKey
    var movieId = 0

    companion object {
        fun createShowEntityFromShow(show: Show): ShowEntity = ShowEntity().apply {
            movieId = show.id
        }
    }

    override fun toString(): String {
        return "$movieId"
    }

    fun createShow() = Show(
        id = movieId,
        url = "",
        name = "String",
        image = mapOf("" to ""),
        summary = "",
        genres = listOf(""),
        schedule = Schedule(
            time = "",
            days = listOf("")
        )
    )
}