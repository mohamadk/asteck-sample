package com.astek.listing.loadmovie

import javax.inject.Inject

class SecretsDataStoreImpl @Inject constructor() : SecretsDataStore {
    override val apiKey: String
        get() = "b9bd48a6"
}
