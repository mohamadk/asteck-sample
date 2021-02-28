package com.astek.di

import com.astek.App
import com.astek.MoviesListingActivity
import com.astek.listing.MovieListingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindsMovieListingFragment(): MovieListingFragment

    @ContributesAndroidInjector
    abstract fun bindsMoviesListingActivity(): MoviesListingActivity

    @ContributesAndroidInjector
    abstract fun bindsApp(): App
}
