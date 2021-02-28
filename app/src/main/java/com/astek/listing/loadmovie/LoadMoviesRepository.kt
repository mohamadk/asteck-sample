package com.astek.listing.loadmovie

import com.astek.listing.LoadMoviesParams
import com.astek.listing.LoadMoviesQuery
import io.reactivex.Observable

interface LoadMoviesRepository {

    fun load(loadMoviesParams: LoadMoviesParams): Observable<MoviesResponse>
}
