package com.astek.listing

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astek.listing.adapter.ItemMoviesModelWrapper
import com.astek.listing.loadmovie.LoadMoviesException
import com.astek.listing.loadmovie.LoadMoviesUseCase
import com.astek.listing.mappers.ItemMovieModelToWrapperMapper
import com.astek.listing.mappers.ViewModelStateToViewStateMapper
import com.astek.utils.disposeBy
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieListingFragmentViewModel @Inject constructor(
    private val loadMoviesUseCase: LoadMoviesUseCase,
    private val itemMovieModelToWrapperMapper: ItemMovieModelToWrapperMapper,
    private val viewModelStateToViewStateMapper: ViewModelStateToViewStateMapper
) : ViewModel() {

    val retrySubject = PublishSubject.create<Unit>()
    private val loadMoviesPublishSubject = PublishSubject.create<LoadMoviesQuery>()
    private val _viewStateLiveData = MutableLiveData<ViewState>()
    val viewStateLiveData: LiveData<ViewState> = _viewStateLiveData
    private val compositeDisposable = CompositeDisposable()
    private val allMovieItems: MutableList<ItemMoviesModelWrapper<*>> = mutableListOf()
    private var availableCount: Int = 0
    private lateinit var lastLoadMoviesQuery: LoadMoviesQuery

    init {
        loadMoviesPublishSubject
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter {
                !it.searchQuery.isNullOrBlank()
            }
            .distinctUntilChanged()
            .doOnNext {
                lastLoadMoviesQuery = it
            }
            .mergeWith(retrySubject.map { lastLoadMoviesQuery })
            .flatMap { loadMoviesQuery ->
                val nextPage = calculateNextPage(loadMoviesQuery)
                if (nextPage > 0) {
                    Observable.just(LoadMoviesParams(nextPage, loadMoviesQuery.searchQuery))
                } else {
                    Observable.error(NoMoreItemAvailableException())
                }
            }.flatMap { loadMoviesParams ->
                loadMoviesUseCase.run(loadMoviesParams)
                    .subscribeOn(Schedulers.io())
                    .map { movieResponse ->
                        val items = movieResponse.items.map { itemMovieModel ->
                            itemMovieModelToWrapperMapper.map(
                                itemMovieModel
                            )
                        }
                        if(loadMoviesParams.isInitialLoad){
                            allMovieItems.clear()
                        }
                        allMovieItems.addAll(items)
                        availableCount = movieResponse.availableCount
                        ViewModelState.Success(
                            !loadMoviesParams.isInitialLoad,
                            allMovieItems
                        ) as ViewModelState
                    }.onErrorReturn {
                        if (it is LoadMoviesException) {
                            if (it.e is NoMoreItemAvailableException) {
                                ViewModelState.NoMoreItemAvailable
                            } else {
                                ViewModelState.Failure(!it.params.isInitialLoad, it.e)
                            }
                        } else {
                            ViewModelState.Failure(!loadMoviesParams.isInitialLoad, it)
                        }
                    }
                    .startWith (
                        ViewModelState.Loading(!loadMoviesParams.isInitialLoad)
                    )
            }
            .map { viewModelState ->
                viewModelStateToViewStateMapper.map(viewModelState)
            }
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

    fun search(searchQuery: String) {
        availableCount = 0
        loadMoviesPublishSubject.onNext(LoadMoviesQuery(true, searchQuery = searchQuery))
    }

    fun onEndOfListReached() {
        loadMoviesPublishSubject.onNext(
            LoadMoviesQuery(
                false,
                allMovieItems.size,
                availableItems = availableCount
            )
        )
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
        val items: List<ItemMoviesModelWrapper<*>>
    ) : ViewModelState()

    data class Failure(val paging: Boolean = false, val error: Throwable?) : ViewModelState()
    object NoMoreItemAvailable : ViewModelState()
}

data class ViewState(
    val showInitialLoading: Boolean = false,
    val showPagingLoading: Boolean = false,
    val initialErrorMessage: Throwable? = null,
    val pagingErrorMessage: Throwable? = null,
    val items: List<ItemMoviesModelWrapper<*>>? = null
)
