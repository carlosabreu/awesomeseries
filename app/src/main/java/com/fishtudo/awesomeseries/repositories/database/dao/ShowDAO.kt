package com.fishtudo.awesomeseries.repositories.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.fishtudo.awesomeseries.repositories.database.entity.ShowEntity

@Dao
interface ShowDAO {

    @Insert
    fun save(show: ShowEntity?): Long?

    @Query("SELECT * FROM ShowEntity")
    fun listAll(): List<ShowEntity?>?

    @Delete
    fun delete(show: ShowEntity?)
}