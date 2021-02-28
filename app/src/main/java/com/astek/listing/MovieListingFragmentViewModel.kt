package com.astek.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astek.listing.adapter.ItemMoviesModelWrapper
import com.astek.listing.loadmovie.LoadMoviesUseCase
import com.astek.listing.mappers.ItemMovieModelToWrapperMapper
import com.astek.listing.mappers.ViewModelStateToViewStateMapper
import com.astek.utils.disposeBy
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MovieListingFragmentViewModel @Inject constructor(
    private val loadMoviesUseCase: LoadMoviesUseCase,
    private val itemMovieModelToWrapperMapper: ItemMovieModelToWrapperMapper,
    private val viewModelStateToViewStateMapper: ViewModelStateToViewStateMapper
) : ViewModel() {

    private val loadMoviesPublishSubject = PublishSubject.create<LoadMoviesQuery>()
    private val _viewStateLiveData = MutableLiveData<ViewState>()
    val viewStateLiveData: LiveData<ViewState> = _viewStateLiveData
    private val compositeDisposable = CompositeDisposable()

    init {
        loadMoviesPublishSubject
            .flatMap { loadMoviesQuery ->
                val nextPage = calculateNextPage(loadMoviesQuery)
                if (nextPage > 0) {
                    Observable.just(LoadMoviesParams(nextPage, loadMoviesQuery.searchQuery))
                } else {
                    Observable.error(NoMoreItemAvailableException())
                }
            }.flatMap { loadMoviesParams ->
                loadMoviesUseCase.run(loadMoviesParams)
                    .map { movieResponse ->
                        val items = movieResponse.items.map { itemMovieModel ->
                            itemMovieModelToWrapperMapper.map(
                                itemMovieModel
                            )
                        }
                        ViewModelState.Success(
                            !loadMoviesParams.isInitialLoad,
                            items,
                            movieResponse.availableCount
                        ) as ViewModelState
                    }
                    .onErrorReturn {
                        if (it is NoMoreItemAvailableException) {
                            ViewModelState.NoMoreItemAvailable
                        } else {
                            ViewModelState.Failure(
                                !loadMoviesParams.isInitialLoad,
                                it.localizedMessage
                            )
                        }
                    }
                    .startWith(
                        ViewModelState.Loading(!loadMoviesParams.isInitialLoad)
                    )
            }
            .map { viewModelState ->
                viewModelStateToViewStateMapper.map(viewModelState)
            }
            .subscribeOn(Schedulers.io())
            .subscribe({
                _viewStateLiveData.postValue(it)
            }, { e ->
                e.printStackTrace()
            }
            ).disposeBy(compositeDisposable)
    }

    private fun calculateNextPage(loadMoviesQuery: LoadMoviesQuery): Int {
        return if (loadMoviesQuery.isInitialLoad) {
            1
        } else {
            val remainingItems = loadMoviesQuery.availableItems - loadMoviesQuery.count
            if (remainingItems > 0) {
                loadMoviesQuery.page + 1
            } else {
                -1
            }
        }
    }

    fun onCreate() {
        loadMoviesPublishSubject.onNext(LoadMoviesQuery(true))
    }

    fun viewCreated() {

    }

    fun onEndOfListReached(count: Int, availableItems: Int) {
        loadMoviesPublishSubject.onNext(LoadMoviesQuery(false, count, availableItems = availableItems))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}

class LoadMoviesQuery(
    val isInitialLoad: Boolean,
    val count: Int = -1,
    val searchQuery: String? = null,
    val availableItems: Int = -1,
    val page: Int = 1
)

class LoadMoviesParams(
    val nextPage: Int,
    val searchQuery: String?
){
    val isInitialLoad = nextPage == 1
}

sealed class ViewModelState {
    data class Loading(val paging: Boolean = false) : ViewModelState()
    data class Success(
        val paging: Boolean = false,
        val items: List<ItemMoviesModelWrapper<*>>,
        val availableCount: Int
    ) : ViewModelState()

    data class Failure(val paging: Boolean = false, val error: String?) : ViewModelState()
    object NoMoreItemAvailable : ViewModelState()
}

data class ViewState(
    val showInitialLoading: Boolean = false,
    val showPagingLoading: Boolean = false,
    val initialErrorMessage: String? = null,
    val pagingErrorMessage: String? = null,
    val pagingItems: List<ItemMoviesModelWrapper<*>>? = null,
    val initialItems: List<ItemMoviesModelWrapper<*>>? = null
)
