package com.astek

import com.astek.listing.MoviesResponse
import com.astek.listing.ViewState
import com.astek.listing.adapter.ItemMoviesModelWrapper
import com.astek.listing.adapter.MovieListingItemTypes
import com.astek.listing.adapter.listing.ItemMovieModel
import com.astek.utils.InstantExecutorExtension

import io.reactivex.Observable
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class MovieListingViewModelTest {

    private val items = listOf(
        ItemMovieModel("title", "imageurl")
    )
    private val itemsWrapper: List<ItemMoviesModelWrapper<*>> = listOf(
        ItemMoviesModelWrapper(
            MovieListingItemTypes.SingleTitleItemType,
            ItemMovieModel("title", "imageurl")
        )
    )
    private val errorMessage = "something went wrong :O"
    private val initialLoading = ViewState(showInitialLoading = true)
    private val pagingLoading = ViewState(showPagingLoading = true)
    private val initialSuccess = ViewState(initialItems = itemsWrapper)
    private val pagingSuccess = ViewState(pagingItems = itemsWrapper)
    private val initialFailure = ViewState(initialErrorMessage = errorMessage)
    private val pagingFailure = ViewState(pagingErrorMessage = errorMessage)
    private val error = IllegalStateException(errorMessage)

    @Test
    fun `initial load items and success`() {
        MovieListingFragmentViewModelRobo(Observable.just(MoviesResponse(items, items.size)))
            .onCreate()
            .verify(initialLoading, initialSuccess)
    }

    @Test
    fun `initial load items and failure`() {
        MovieListingFragmentViewModelRobo(Observable.error(error))
            .onCreate()
            .verify(initialLoading, initialFailure)
    }

    @Test
    fun `paging load items and success`() {
        MovieListingFragmentViewModelRobo(Observable.just(MoviesResponse(items, items.size)))
            .onEndOfListReached(1)
            .verify(pagingLoading, pagingSuccess)
    }

    @Test
    fun `paging load items and failure`() {
        MovieListingFragmentViewModelRobo(Observable.error(error))
            .onEndOfListReached(1)
            .verify(pagingLoading, pagingFailure)
    }
}
