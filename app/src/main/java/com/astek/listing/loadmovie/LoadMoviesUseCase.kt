package com.astek.listing.loadmovie

import com.astek.listing.LoadMoviesParams
import com.astek.listing.LoadMoviesQuery
import com.astek.listing.adapter.listing.ItemMovieModel
import com.astek.utils.UseCase
import io.reactivex.Observable

class LoadMoviesUseCase(private val loadMoviesRepository: LoadMoviesRepository) :
    UseCase<LoadMoviesParams, Observable<MoviesResponse>> {

    override fun run(params: LoadMoviesParams): Observable<MoviesResponse> {
        return loadMoviesRepository.load(params)
    }
}

class MoviesResponse(val items: List<ItemMovieModel>, val availableCount: Int)
