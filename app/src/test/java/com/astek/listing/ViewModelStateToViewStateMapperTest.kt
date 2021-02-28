package com.astek.listing

import com.astek.listing.adapter.ItemMoviesModelWrapper
import com.astek.listing.adapter.MovieListingItemTypes
import com.astek.listing.adapter.listing.ItemMovieModel
import com.astek.listing.mappers.ViewModelStateToViewStateMapper
import junit.framework.Assert.assertEquals
import org.junit.Test

internal class ViewModelStateToViewStateMapperTest {

    private val items: List<ItemMoviesModelWrapper<*>> = listOf(
        ItemMoviesModelWrapper(
            MovieListingItemTypes.SingleTitleItemType,
            ItemMovieModel("title", "imageurl")
        )
    )
    private val errorMessage = "error message"

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
        val initialSuccess = ViewModelState.Success(items = items, availableCount = 1)
        assertEquals(
            ViewState(initialItems = items),
            ViewModelStateToViewStateMapper().map(initialSuccess)
        )
    }

    @Test
    fun `map paging success`() {
        val pagingSuccess = ViewModelState.Success(paging = true, items = items, availableCount = 1)
        assertEquals(
            ViewState(pagingItems = items),
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
}
