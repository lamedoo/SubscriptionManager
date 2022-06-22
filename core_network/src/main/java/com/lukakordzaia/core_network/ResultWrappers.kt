package com.lukakordzaia.core_network

sealed class ResultNetwork<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResultNetwork<T>()
    data class Error(val exception: Any) : ResultNetwork<Nothing>()
}