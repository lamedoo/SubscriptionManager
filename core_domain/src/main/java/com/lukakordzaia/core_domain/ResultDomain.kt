package com.lukakordzaia.core_domain

sealed class ResultDomain<out Success : Any, out Error : Any> {
    data class Success<out Success : Any>(val data: Success) : ResultDomain<Success, Nothing>()
    data class Error<out Error : Any>(val exception: Error) : ResultDomain<Nothing, Error>()
}