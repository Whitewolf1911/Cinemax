package com.alibasoglu.cinemax.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alibasoglu.cinemax.data.local.model.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract val dao: MovieDao
}
