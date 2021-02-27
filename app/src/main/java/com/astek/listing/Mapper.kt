package com.astek.listing

interface Mapper<T, U> {

    fun map(input:T):U
}
