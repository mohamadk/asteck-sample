package com.astek.listing

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MovieListingFragmentViewModel @Inject constructor(
    private val loadMoviesUseCase: LoadMoviesUseCase
) : ViewModel() {

    fun onCreate() {
        loadMoviesUseCase.load()
    }

    fun viewCreated() {

    }


}
