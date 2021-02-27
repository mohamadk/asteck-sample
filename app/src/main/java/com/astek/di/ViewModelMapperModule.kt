package com.astek.di

import androidx.lifecycle.ViewModel
import com.astek.listing.MovieListingFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelMapperModule {

    @IntoMap
    @Binds
    @ViewModelKey(MovieListingFragmentViewModel::class)
    abstract fun bindMovieListingFragmentViewModel(viewModel: MovieListingFragmentViewModel)
            : ViewModel

}
