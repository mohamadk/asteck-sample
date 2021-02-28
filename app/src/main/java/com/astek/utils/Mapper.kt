package com.astek.utils

interface Mapper<T, U> {

    fun map(input:T):U
}
