package com.astek.utils

interface UseCase<INPUT, OUTPUT> {

    fun run(input: INPUT): OUTPUT

}
