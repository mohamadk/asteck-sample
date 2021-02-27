package com.astek.listing

interface UseCase<INPUT, OUTPUT> {

    fun run(input: INPUT): OUTPUT

}
