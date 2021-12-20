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
    var url = ""
    var name = ""
    var image = mapOf<String, String>()
    var summary = ""
    var scheduleTime = ""
    var scheduleDays = listOf("")
    var genres = listOf("")

    companion object {
        fun createShowEntityFromShow(show: Show): ShowEntity = ShowEntity().apply {
            movieId = show.id
            url = show.url
            name = show.name
            summary = show.summary
            image = show.image ?: mapOf()
            scheduleTime = show.schedule.time
            scheduleDays = show.schedule.days
            genres = show.genres
        }
    }

    override fun toString(): String {
        return "$movieId"
    }

    fun createShow() = Show(
        id = movieId,
        url = this.url,
        name = this.name,
        image = this.image,
        summary = this.summary,
        genres = this.genres,
        schedule = Schedule(
            time = this.scheduleTime,
            days = this.scheduleDays
        )
    )
}