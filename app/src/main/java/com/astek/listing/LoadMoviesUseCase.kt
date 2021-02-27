package com.astek.listing

import com.astek.listing.adapter.listing.ItemMovieModel
import io.reactivex.Observable

class LoadMoviesUseCase(private val loadMoviesRepository: LoadMoviesRepository) :
    UseCase<LoadMoviesParams, Observable<MoviesResponse>> {

    override fun run(params: LoadMoviesParams): Observable<MoviesResponse> {
        return loadMoviesRepository.load(params)
    }
}

class MoviesResponse(val items: List<ItemMovieModel>, val availableCount: Int)
