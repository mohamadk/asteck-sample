package com.astek.listing.loadmovie

import android.util.Log
import com.astek.listing.LoadMoviesParams
import io.reactivex.Observable
import javax.inject.Inject

class LoadMoviesRepositoryImpl @Inject constructor(
    private val loadMoviesRemoteDataStore: LoadMoviesRemoteDataStore,
    private val secretsDataStore:SecretsDataStore
) : LoadMoviesRepository {

    override fun load(loadMoviesParams: LoadMoviesParams): Observable<MoviesResponse> {
        return loadMoviesRemoteDataStore.load(
            secretsDataStore.apiKey,
            loadMoviesParams.nextPage,
            loadMoviesParams.searchQuery
        )
    }
}
