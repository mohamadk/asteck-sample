package com.astek.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astek.listing.adapter.ItemMoviesModelWrapper
import com.astek.listing.loadmovie.LoadMoviesUseCase
import com.astek.listing.mappers.ItemMovieModelToWrapperMapper
import com.astek.listing.mappers.ViewModelStateToViewStateMapper
import com.astek.utils.disposeBy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MovieListingFragmentViewModel @Inject constructor(
    private val loadMoviesUseCase: LoadMoviesUseCase,
    private val itemMovieModelToWrapperMapper: ItemMovieModelToWrapperMapper,
    private val viewModelStateToViewStateMapper: ViewModelStateToViewStateMapper
) : ViewModel() {

    private val loadMoviesPublishSubject = PublishSubject.create<LoadMoviesParams>()
    private val _viewStateLiveData = MutableLiveData<ViewState>()
    val viewStateLiveData: LiveData<ViewState> = _viewStateLiveData
    private val compositeDisposable = CompositeDisposable()

    init {
        loadMoviesPublishSubject.flatMap { loadMoviesParams ->
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
                    ViewModelState.Failure(!loadMoviesParams.isInitialLoad, it.localizedMessage)
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

    fun onCreate() {
        loadMoviesPublishSubject.onNext(LoadMoviesParams(true))
    }

    fun viewCreated() {

    }

    fun onEndOfListReached(count: Int) {
        loadMoviesPublishSubject.onNext(LoadMoviesParams(false, count))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}

class LoadMoviesParams(val isInitialLoad: Boolean, val count: Int = -1)

sealed class ViewModelState {
    data class Loading(val paging: Boolean = false) : ViewModelState()
    data class Success(
        val paging: Boolean = false,
        val items: List<ItemMoviesModelWrapper<*>>,
        val availableCount: Int
    ) : ViewModelState()

    data class Failure(val paging: Boolean = false, val error: String?) : ViewModelState()
}

data class ViewState(
    val showInitialLoading: Boolean = false,
    val showPagingLoading: Boolean = false,
    val initialErrorMessage: String? = null,
    val pagingErrorMessage: String? = null,
    val pagingItems: List<ItemMoviesModelWrapper<*>>? = null,
    val initialItems: List<ItemMoviesModelWrapper<*>>? = null
)
