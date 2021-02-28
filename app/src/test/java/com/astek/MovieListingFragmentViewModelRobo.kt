package com.astek

import androidx.lifecycle.Observer
import com.astek.listing.MovieListingFragmentViewModel
import com.astek.listing.ViewState
import com.astek.listing.loadmovie.LoadMoviesUseCase
import com.astek.listing.loadmovie.MoviesResponse
import com.astek.listing.mappers.ItemMovieModelToWrapperMapper
import com.astek.listing.mappers.ViewModelStateToViewStateMapper
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import junit.framework.Assert.assertEquals


class MovieListingFragmentViewModelRobo(vararg loadMovieResponses: Observable<MoviesResponse>) {

    private val loadMoviesUseCase: LoadMoviesUseCase = mock()
    private val itemMovieModelToWrapperMapper = ItemMovieModelToWrapperMapper()
    private val viewModelStateToViewStateMapper = ViewModelStateToViewStateMapper()
    private val viewModel: MovieListingFragmentViewModel
    private val viewStateObserver: Observer<ViewState> = mock()

    init {
        if (loadMovieResponses.size > 1) {
            whenever(loadMoviesUseCase.run(any())).thenReturn(
                loadMovieResponses[0],
                *loadMovieResponses.sliceArray(1 until loadMovieResponses.size)
            )
        } else {
            whenever(loadMoviesUseCase.run(any())).thenReturn(loadMovieResponses[0])
        }
        viewModel = MovieListingFragmentViewModel(
            loadMoviesUseCase,
            itemMovieModelToWrapperMapper,
            viewModelStateToViewStateMapper
        )
        viewModel.viewStateLiveData.observeForever(viewStateObserver)
    }

    fun search(searchQuery: String): MovieListingFragmentViewModelRobo {
        viewModel.search(searchQuery)
        return this
    }

    fun verify(vararg viewStates: ViewState) {
        val argCapture = argumentCaptor<ViewState>()
        verify(viewStateObserver, times(viewStates.size)).onChanged(argCapture.capture())
        assertEquals(viewStates.toList(), argCapture.allValues)
    }

    fun onEndOfListReached(): MovieListingFragmentViewModelRobo {
        viewModel.onEndOfListReached()
        return this
    }

}
