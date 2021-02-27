package com.astek.listing

import io.reactivex.Observable

interface LoadMoviesRepository {
    fun load(loadMoviesParams: LoadMoviesParams): Observable<MoviesResponse>

}
