package com.astek.listing.mappers

import com.astek.listing.adapter.ItemMoviesModelWrapper
import com.astek.listing.adapter.MovieListingItemTypes
import com.astek.listing.adapter.listing.ItemMovieModel
import com.astek.utils.Mapper
import javax.inject.Inject

class ItemMovieModelToWrapperMapper @Inject constructor() :
    Mapper<ItemMovieModel, ItemMoviesModelWrapper<*>> {

    override fun map(input: ItemMovieModel): ItemMoviesModelWrapper<*> {
        return ItemMoviesModelWrapper(MovieListingItemTypes.SingleTitleItemType, input)
    }
}
