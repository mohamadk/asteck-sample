package com.astek.listing.adapter

import com.astek.listing.adapter.listing.LoadingItem
import com.astek.listing.adapter.listing.SingleTitleItem
import com.mikepenz.fastadapter.items.ModelAbstractItem

class MoviesItemFactory() : (MoviesModelItemWrapper<*>) -> ModelAbstractItem<*, *> {
    override fun invoke(itemWrapper: MoviesModelItemWrapper<*>): ModelAbstractItem<*, *> {
        return when (itemWrapper.type) {
            MovieListingItemTypes.SingleTitleItemType -> {
                SingleTitleItem(itemWrapper)
            }
            MovieListingItemTypes.LoadingItemType -> {
                LoadingItem(itemWrapper)
            }
        }
    }
}
