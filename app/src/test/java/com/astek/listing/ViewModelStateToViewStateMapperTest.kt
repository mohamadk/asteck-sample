package com.astek.listing

import com.astek.listing.adapter.ItemMoviesModelWrapper
import com.astek.listing.adapter.MovieListingItemTypes
import com.astek.listing.adapter.listing.ItemMovieModel
import com.astek.listing.mappers.ViewModelStateToViewStateMapper
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.lang.IllegalStateException

internal class ViewModelStateToViewStateMapperTest {

    private val items: List<ItemMoviesModelWrapper<*>> = listOf(
        ItemMoviesModelWrapper(
            MovieListingItemTypes.SingleTitleItemType,
            ItemMovieModel("title", "imageurl")
        )
    )
    private val errorMessage = IllegalStateException("error message")

    @Test
    fun `map initial Loading`() {
        val initialLoading = ViewModelState.Loading()
        assertEquals(
            ViewState(showInitialLoading = true),
            ViewModelStateToViewStateMapper().map(initialLoading)
        )
    }

    @Test
    fun `map paging loading`() {
        val pagingLoading = ViewModelState.Loading(paging = true)
        assertEquals(
            ViewState(showPagingLoading = true),
            ViewModelStateToViewStateMapper().map(pagingLoading)
        )
    }

    @Test
    fun `map initial success`() {
        val initialSuccess = ViewModelState.Success(items = items)
        assertEquals(
            ViewState(items = items),
            ViewModelStateToViewStateMapper().map(initialSuccess)
        )
    }

    @Test
    fun `map paging success`() {
        val pagingSuccess = ViewModelState.Success(paging = true, items = items)
        assertEquals(
            ViewState(items = items),
            ViewModelStateToViewStateMapper().map(pagingSuccess)
        )
    }


    @Test
    fun `map initial Failure`() {
        val initialFailure = ViewModelState.Failure(error = errorMessage)
        assertEquals(
            ViewState(initialErrorMessage = errorMessage),
            ViewModelStateToViewStateMapper().map(initialFailure)
        )
    }

    @Test
    fun `map paging failure`() {
        val pagingFailure = ViewModelState.Failure(paging = true, error = errorMessage )
        assertEquals(
            ViewState(pagingErrorMessage = errorMessage),
            ViewModelStateToViewStateMapper().map(pagingFailure)
        )
    }

    @Test
    fun `map no more Item available`() {
        val pagingFailure = ViewModelState.NoMoreItemAvailable
        assertEquals(
            ViewState(),
            ViewModelStateToViewStateMapper().map(pagingFailure)
        )
    }
}
