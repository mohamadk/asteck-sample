package com.astek

import androidx.lifecycle.Observer
import com.astek.listing.*
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import junit.framework.Assert.assertEquals


class MovieListingFragmentViewModelRobo(
    private val testScheduler: TestScheduler,
    loadMovieResponse: Observable<MoviesResponse>
) {

    private val loadMoviesUseCase: LoadMoviesUseCase = mock()
    private val itemMovieModelToWrapperMapper = ItemMovieModelToWrapperMapper()
    private val viewModelStateToViewStateMapper = ViewModelStateToViewStateMapper()
    private val viewModel: MovieListingFragmentViewModel
    private val viewStateObserver: Observer<ViewState> = mock()

    init {
        whenever(loadMoviesUseCase.run(any())).thenReturn(loadMovieResponse)
        viewModel = MovieListingFragmentViewModel(
            loadMoviesUseCase,
            itemMovieModelToWrapperMapper,
            viewModelStateToViewStateMapper
        )
        viewModel.viewStateLiveData.observeForever(viewStateObserver)
    }

    fun onCreate(): MovieListingFragmentViewModelRobo {
        testScheduler.triggerActions()
        viewModel.onCreate()
        testScheduler.triggerActions()
        return this
    }

    fun verify(vararg viewStates: ViewState) {
        val argCapture = argumentCaptor<ViewState>()
        verify(viewStateObserver, times(viewStates.size)).onChanged(argCapture.capture())
        assertEquals(viewStates.toList(), argCapture.allValues)
    }

}
