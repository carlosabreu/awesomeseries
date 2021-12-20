package com.fishtudo.awesomeseries.repositories.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fishtudo.awesomeseries.repositories.database.converter.ListConverter
import com.fishtudo.awesomeseries.repositories.database.converter.MapConverter
import com.fishtudo.awesomeseries.repositories.database.dao.ShowDAO
import com.fishtudo.awesomeseries.repositories.database.entity.ShowEntity

private const val DATABASE_NAME = "favorites.db"

@Database(entities = [ShowEntity::class], version = 1, exportSchema = false)
@TypeConverters(MapConverter::class, ListConverter::class)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract val showDAO: ShowDAO?

    companion object {
        fun getInstance(context: Context?): FavoriteDatabase =
            Room.databaseBuilder(context!!, FavoriteDatabase::class.java, DATABASE_NAME)
                .build()
    }
}