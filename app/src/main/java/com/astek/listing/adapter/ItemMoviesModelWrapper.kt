package com.astek.listing.adapter

import com.astek.listing.adapter.ItemMoviesModelWrapper.Companion.LOADING_ITEM
import com.astek.listing.adapter.ItemMoviesModelWrapper.Companion.MOVIE_LISTING_ITEM

sealed class MovieListingItemTypes {
    object SingleTitleItemType : MovieListingItemTypes(){
        const val value: Int = MOVIE_LISTING_ITEM
    }
    object LoadingItemType : MovieListingItemTypes(){
        const val value: Int = LOADING_ITEM
    }
}

data class ItemMoviesModelWrapper<MODEL>(
    val type: MovieListingItemTypes,
    val model: MODEL
) {
    companion object {
        const val MOVIE_LISTING_ITEM = 1001
        const val LOADING_ITEM = 1002
    }
}
