package com.astek.di

import com.astek.listing.loadmovie.*
import dagger.Binds
import dagger.Module

@Module
abstract class ListingBindModule {

    @Binds
    abstract fun provideLoadMoviesRepository(loadMoviesRepositoryImpl: LoadMoviesRepositoryImpl):
            LoadMoviesRepository

    @Binds
    abstract fun provideSecretsDataStore(secretsDataStoreImpl: SecretsDataStoreImpl):
            SecretsDataStore

}
