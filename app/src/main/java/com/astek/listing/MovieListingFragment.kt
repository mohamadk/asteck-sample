package com.astek.listing

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astek.R
import com.astek.di.ViewModelFactory
import com.astek.listing.adapter.ItemMoviesModelWrapper
import com.astek.listing.adapter.MoviesItemFactory
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.adapters.ModelAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.fastadapter.items.ModelAbstractItem
import com.mikepenz.fastadapter.ui.items.ProgressItem
import kotlinx.android.synthetic.main.fragment_movie_listing.*
import javax.inject.Inject

class MovieListingFragment : Fragment(R.layout.fragment_movie_listing) {

    companion object {
        @JvmStatic
        fun newInstance() = MovieListingFragment()
        const val NUMBER_OF_COLUMNS_IN_LIST = 2
    }

    private val moviesItemFactory = MoviesItemFactory()
    private val itemAdapter: ModelAdapter<ItemMoviesModelWrapper<*>, ModelAbstractItem<*, *>> =
        ModelAdapter(moviesItemFactory)
    private val footerAdapter = ItemAdapter<ProgressItem>()
    private val fastAdapter: FastAdapter<AbstractItem<out RecyclerView.ViewHolder>> =
        FastAdapter.with(listOf(itemAdapter, footerAdapter))

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: MovieListingFragmentViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, viewModelFactory)[MovieListingFragmentViewModel::class.java]
            .apply {
                viewStateLiveData.observe(this@MovieListingFragment) { viewstate ->
                    updateViewState(viewstate)
                }
            }
    }

    private fun updateViewState(viewstate: ViewState) {
        with(viewstate) {
            initialProgressBar.isVisible = showInitialLoading
            if (showPagingLoading) {
                showPagingLoading()
            } else {
                hidePagingLoading()
            }
            items?.let {
                submitInitialItems(items)
            }
            errorView.isVisible = initialErrorMessage != null || pagingErrorMessage != null
        }
    }

    private fun submitInitialItems(items: List<ItemMoviesModelWrapper<*>>) {
        itemAdapter.set(items)
    }

    private fun hidePagingLoading() {
        footerAdapter.clear()
    }

    private fun showPagingLoading() {
        footerAdapter.add(ProgressItem())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesRecyclerview.apply {
            layoutManager = GridLayoutManager(requireActivity(), NUMBER_OF_COLUMNS_IN_LIST)
            adapter = fastAdapter
        }

        viewModel.viewCreated()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate()
    }



}
