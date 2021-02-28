package com.astek.listing.loadmovie

import com.astek.listing.LoadMoviesParams
import io.reactivex.Observable

interface LoadMoviesRepository {

    fun load(loadMoviesParams: LoadMoviesParams): Observable<MoviesResponse>
}
