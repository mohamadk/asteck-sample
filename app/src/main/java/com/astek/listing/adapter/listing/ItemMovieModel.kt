package com.astek.listing.adapter.listing

import com.google.gson.annotations.SerializedName

data class ItemMovieModel(
    @SerializedName("Title") val title: String?,
    @SerializedName("Poster") val image: String?
)
