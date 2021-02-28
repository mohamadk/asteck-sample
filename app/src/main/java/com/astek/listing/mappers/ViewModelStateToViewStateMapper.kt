package com.astek.listing.mappers

import com.astek.listing.ViewModelState
import com.astek.listing.ViewState
import com.astek.utils.Mapper
import javax.inject.Inject

class ViewModelStateToViewStateMapper @Inject constructor() : Mapper<ViewModelState, ViewState> {

    override fun map(viewModelState: ViewModelState): ViewState {
        return when (viewModelState) {
            is ViewModelState.Loading -> {
                ViewState(
                    showInitialLoading = !viewModelState.paging,
                    showPagingLoading = viewModelState.paging
                )
            }
            is ViewModelState.Failure -> {
                ViewState(
                    initialErrorMessage = if (!viewModelState.paging) {
                        viewModelState.error
                    } else {
                        null
                    },
                    pagingErrorMessage = if (viewModelState.paging) {
                        viewModelState.error
                    } else {
                        null
                    }
                )
            }
            is ViewModelState.Success -> {
                ViewState(
                    pagingItems = if (viewModelState.paging) {
                        viewModelState.items
                    } else {
                        null
                    },
                    initialItems = if (!viewModelState.paging) {
                        viewModelState.items
                    } else {
                        null
                    }
                )
            }
            ViewModelState.NoMoreItemAvailable -> {
                ViewState()
            }
        }
    }
}
