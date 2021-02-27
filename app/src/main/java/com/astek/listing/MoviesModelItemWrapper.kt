package com.astek.listing

import com.astek.listing.MoviesModelItemWrapper.Companion.LOADING_ITEM
import com.astek.listing.MoviesModelItemWrapper.Companion.MOVIE_LISTING_ITEM

sealed class MovieListingItemTypes {
    object SingleTitleItemType : MovieListingItemTypes(){
        const val value: Int = MOVIE_LISTING_ITEM
    }
    object LoadingItemType : MovieListingItemTypes(){
        const val value: Int = LOADING_ITEM
    }
}

class MoviesModelItemWrapper<MODEL>(
    val model: MODEL,
    val type: MovieListingItemTypes
) {
    companion object {
        const val MOVIE_LISTING_ITEM = 1001
        const val LOADING_ITEM = 1002
    }
}
