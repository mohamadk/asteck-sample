package com.astek.listing.loadmovie

import io.reactivex.Observable

interface LoadMoviesRemoteDataStore {

    fun load(nextPage: Int, searchQuery: String?): Observable<MoviesResponse>
}
