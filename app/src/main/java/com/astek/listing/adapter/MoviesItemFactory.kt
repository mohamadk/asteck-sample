package com.astek.listing.adapter

import com.astek.listing.adapter.listing.SingleTitleItem
import com.mikepenz.fastadapter.items.ModelAbstractItem

class MoviesItemFactory : (ItemMoviesModelWrapper<*>) -> ModelAbstractItem<*, *> {

    override fun invoke(itemWrapper: ItemMoviesModelWrapper<*>): ModelAbstractItem<*, *> {
        return when (itemWrapper.type) {
            MovieListingItemTypes.SingleTitleItemType -> {
                SingleTitleItem(itemWrapper)
            }
        }
    }
}
