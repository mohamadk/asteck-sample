package com.astek.listing.loadmovie

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface LoadMoviesRemoteDataStore {

    @GET("/")
    fun load(
        @Query("apikey") apiKey: String,
        @Query("page") nextPage: Int,
        @Query("s") searchQuery: String?
    ): Observable<MoviesResponse>
}
