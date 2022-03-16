package com.lukakordzaia.subscriptionmanager.network

sealed class ResultNetwork<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResultNetwork<T>()
    data class Error(val exception: Any) : ResultNetwork<Nothing>()
}

sealed class ResultDomain<out Success : Any, out Error : Any> {
    data class Success<out Success : Any>(val data: Success) : ResultDomain<Success, Nothing>()
    data class Error<out Error : Any>(val exception: Error) : ResultDomain<Nothing, Error>()
}

enum class LoadingState {
    LOADING,
    LOADED,
    ERROR
}