package com.astek

import com.astek.listing.ViewState
import com.astek.listing.adapter.ItemMoviesModelWrapper
import com.astek.listing.adapter.MovieListingItemTypes
import com.astek.listing.adapter.listing.ItemMovieModel
import com.astek.listing.loadmovie.MoviesResponse
import com.astek.utils.InstantExecutorExtension
import io.reactivex.Observable
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class MovieListingViewModelTest {

    private val items = listOf(
        ItemMovieModel("title", "imageurl")
    )
    private val itemWrapper = ItemMoviesModelWrapper(
        MovieListingItemTypes.SingleTitleItemType,
        ItemMovieModel("title", "imageurl")
    )
    private val itemsWrapper: List<ItemMoviesModelWrapper<*>> = listOf(itemWrapper)

    private val errorMessage = "something went wrong :O"
    private val initialLoading = ViewState(showInitialLoading = true)
    private val pagingLoading = ViewState(showPagingLoading = true)
    private val initialSuccess = ViewState(items = itemsWrapper)
    private val pagingSuccess = ViewState(items = listOf(itemWrapper,itemWrapper))
    private val error = IllegalStateException(errorMessage)
    private val initialFailure = ViewState(initialErrorMessage = error)
    private val pagingFailure = ViewState(pagingErrorMessage = error)
    private val noMoreItemAvailable = ViewState()

    @Test
    fun `initial load items and success`() {
        MovieListingFragmentViewModelRobo(Observable.just(MoviesResponse(items, items.size)))
            .search("marvel")
            .verify(initialLoading, initialSuccess)
    }

    @Test
    fun `initial load items and failure`() {
        MovieListingFragmentViewModelRobo(Observable.error(error))
            .search("marvel")
            .verify(initialLoading, initialFailure)
    }

    @Test
    fun `paging load no more item available`() {
        MovieListingFragmentViewModelRobo(Observable.just(MoviesResponse(items, items.size)))
            .search("d")
            .onEndOfListReached(1)
            .verify(initialLoading, initialSuccess, noMoreItemAvailable)
    }

    @Test
    fun `paging load items and success`() {
        MovieListingFragmentViewModelRobo(
            Observable.just(MoviesResponse(items, items.size + 1)),
            Observable.just(MoviesResponse(items, items.size))
        )
            .search("s")
            .onEndOfListReached(1)
            .verify(initialLoading, initialSuccess, pagingLoading, pagingSuccess)
    }

    @Test
    fun `paging load items and failure`() {
        MovieListingFragmentViewModelRobo(
            Observable.just(MoviesResponse(items, items.size + 1)),
            Observable.error(error)
        )
            .search("s")
            .onEndOfListReached(1)
            .verify(initialLoading, initialSuccess, pagingLoading, pagingFailure)
    }
}
