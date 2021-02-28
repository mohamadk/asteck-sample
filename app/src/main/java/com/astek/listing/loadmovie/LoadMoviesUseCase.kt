package com.astek.listing.loadmovie

import com.astek.listing.LoadMoviesParams
import com.astek.listing.adapter.listing.ItemMovieModel
import com.astek.utils.UseCase
import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import java.lang.Exception
import javax.inject.Inject

class LoadMoviesUseCase @Inject constructor(private val loadMoviesRepository: LoadMoviesRepository) :
    UseCase<LoadMoviesParams, Observable<MoviesResponse>> {

    override fun run(params: LoadMoviesParams): Observable<MoviesResponse> {
        return loadMoviesRepository.load(params)
    }
}

data class MoviesResponse(
    @SerializedName("Search") val items: List<ItemMovieModel>,
    @SerializedName("totalResults") val availableCount: Int
)
