package com.astek

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.astek.listing.MoviesResponse
import com.astek.listing.ViewState
import com.astek.listing.adapter.ItemMoviesModelWrapper
import com.astek.listing.adapter.MovieListingItemTypes
import com.astek.listing.adapter.listing.ItemMovieModel
import com.astek.utils.TestSchedulerRule
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieListingViewModelTest {

    @JvmField
    @get:Rule
    val schedulerRule = TestSchedulerRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

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
        MovieListingFragmentViewModelRobo(
            schedulerRule.testScheduler,
            Observable.just(MoviesResponse(items, items.size))
        )
            .onCreate()
            .verify(initialLoading, initialSuccess)
    }

    @Test
    fun `initial load items and failure`() {
        MovieListingFragmentViewModelRobo(schedulerRule.testScheduler, Observable.error(error))
            .onCreate()
            .verify(initialLoading, initialFailure)
    }


}
