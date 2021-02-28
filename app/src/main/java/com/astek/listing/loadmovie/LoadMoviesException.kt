package com.astek.listing.loadmovie

import com.astek.listing.LoadMoviesParams

class LoadMoviesException(val params: LoadMoviesParams,val e: Exception) : Throwable()
