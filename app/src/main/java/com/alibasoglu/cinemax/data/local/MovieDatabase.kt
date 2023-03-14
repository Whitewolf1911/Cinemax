package com.alibasoglu.cinemax.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alibasoglu.cinemax.data.local.model.MovieEntity
import com.alibasoglu.cinemax.data.local.model.ShowEntity

@Database(
    entities = [MovieEntity::class, ShowEntity::class],
    version = 2,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract val dao: MovieDao
}
