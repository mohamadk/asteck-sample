package com.astek.listing.loadmovie

import com.astek.listing.LoadMoviesParams
import io.reactivex.Observable
import javax.inject.Inject

class LoadMoviesRepositoryImpl @Inject constructor(
    private val loadMoviesRemoteDataStore: LoadMoviesRemoteDataStore
) : LoadMoviesRepository {

    override fun load(loadMoviesParams: LoadMoviesParams): Observable<MoviesResponse> {
        return loadMoviesRemoteDataStore.load(
            loadMoviesParams.nextPage,
            loadMoviesParams.searchQuery
        )
    }
}
