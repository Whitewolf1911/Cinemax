package com.alibasoglu.cinemax.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alibasoglu.cinemax.data.local.model.MovieEntity
import com.alibasoglu.cinemax.data.local.model.ShowEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(
        movieEntity: MovieEntity
    )

    @Query("SELECT * FROM movie_entities")
    suspend fun getWishListedMovies(): List<MovieEntity>

    @Query("DELETE FROM movie_entities WHERE id = :movieId")
    suspend fun deleteMovieEntity(movieId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShow(
        showEntity: ShowEntity
    )

    @Query("SELECT EXISTS(SELECT * FROM movie_entities WHERE id = :movieId)")
    suspend fun checkMovieExists(movieId: Int): Boolean

    @Query("SELECT * FROM show_entities")
    suspend fun getWishListedShows(): List<ShowEntity>

    @Query("DELETE FROM show_entities WHERE id = :showId")
    suspend fun deleteShowEntity(showId: Int)

    @Query("SELECT EXISTS(SELECT * FROM show_entities WHERE id = :showId)")
    suspend fun checkShowExists(showId: Int): Boolean

}
