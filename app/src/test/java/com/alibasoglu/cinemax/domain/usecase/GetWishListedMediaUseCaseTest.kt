package com.alibasoglu.cinemax.domain.usecase

import android.annotation.SuppressLint
import com.alibasoglu.cinemax.data.repository.FakeMovieRepository
import com.alibasoglu.cinemax.moviedetail.domain.model.MovieDetail
import com.alibasoglu.cinemax.moviedetail.domain.model.TvShowDetail
import com.alibasoglu.cinemax.ui.model.WishListCardItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetWishListedMediaUseCaseTest {

    private lateinit var getWishListedMediaUseCase: GetWishListedMediaUseCase
    private lateinit var fakeMovieRepository: FakeMovieRepository

    @Before
    fun setUp() {
        fakeMovieRepository = FakeMovieRepository()
        getWishListedMediaUseCase = GetWishListedMediaUseCase(fakeMovieRepository)

        // Inserting 15 items and checking if all of them received in the test case
        val moviesToInsert = mutableListOf<MovieDetail>()
        val showsToInsert = mutableListOf<TvShowDetail>()
        (1..10).forEachIndexed { _, i ->
            moviesToInsert.add(
                MovieDetail(
                    backdrop_path = null,
                    genre = "",
                    id = i,
                    original_title = "",
                    overview = null,
                    poster_path = null,
                    release_date = "",
                    runtime = 0,
                    title = "",
                    video = false,
                    vote_average = 0.0
                )
            )
        }
        (1..5).forEachIndexed { _, i ->
            showsToInsert.add(
                TvShowDetail(
                    backdrop_path = null,
                    episode_run_time = 0,
                    first_air_date = "",
                    genres = "",
                    id = i,
                    name = "",
                    number_of_episodes = 0,
                    number_of_seasons = 0,
                    original_name = "",
                    overview = "",
                    poster_path = null,
                    vote_average = 0.0
                )
            )
        }
        runBlocking {
            moviesToInsert.forEach {
                fakeMovieRepository.insertMovieToDatabase(it)
            }
            showsToInsert.forEach {
                fakeMovieRepository.insertShowToDatabase(it)
            }
        }
    }

    @SuppressLint("CheckResult")
    @Test
    fun `All wish listed media returns from database`() {
        lateinit var itemList: List<WishListCardItem>
        runBlocking {
            getWishListedMediaUseCase().collectLatest {
                itemList = it
            }
        }
        assertThat(itemList.size == 15).isTrue()
    }

}
