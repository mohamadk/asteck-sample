package com.astek.listing

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
        }
    }
}
