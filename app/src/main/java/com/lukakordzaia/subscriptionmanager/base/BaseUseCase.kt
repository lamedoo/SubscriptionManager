package com.lukakordzaia.subscriptionmanager.base

interface BaseUseCase<ARG_TYPE, RETURN_TYPE> {
    suspend fun invoke(args: ARG_TYPE? = null): RETURN_TYPE
}