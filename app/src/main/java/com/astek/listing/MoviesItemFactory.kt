package com.astek.listing

import com.astek.listing.items.LoadingItem
import com.astek.listing.items.SingleTitleItem
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
