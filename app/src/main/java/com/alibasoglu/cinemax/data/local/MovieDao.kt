package com.alibasoglu.cinemax.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alibasoglu.cinemax.data.local.model.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(
        movieEntity: MovieEntity
    )

    @Query("SELECT * FROM movie_entities")
    suspend fun getWishListedMovies(): List<MovieEntity>
}
