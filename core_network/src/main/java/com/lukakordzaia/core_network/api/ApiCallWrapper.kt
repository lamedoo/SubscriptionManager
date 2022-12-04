package com.lukakordzaia.core_network.api

import com.lukakordzaia.core_network.ResultNetwork
import retrofit2.Response

object ApiCallWrapper {
    suspend fun <T : Any> makeCall(call: suspend () -> Response<T>): ResultNetwork<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                ResultNetwork.Success(response.body()!!)
            } else {
                ResultNetwork.Error(response.code())
            }
        } catch (e: Exception) {
            ResultNetwork.Error(e.toString())
        }
    }
}