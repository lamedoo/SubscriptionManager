package com.lukakordzaia.core_domain

import com.lukakordzaia.core_network.ResultNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseFlowUseCase<ARG_TYPE : Any, NETWORK_TYPE : Any, DOMAIN_TYPE : Any> : BaseUseCase<ARG_TYPE, Flow<ResultDomain<DOMAIN_TYPE, String>>> {
    protected suspend fun transformToDomain(
        call: Flow<ResultNetwork<NETWORK_TYPE>>,
        transform: (data: NETWORK_TYPE) -> DOMAIN_TYPE
    ):Flow<ResultDomain<DOMAIN_TYPE, String>> {
        return flow {
            try {
                call.collect {
                    return@collect when (it) {
                        is ResultNetwork.Success -> {
                            emit(ResultDomain.Success(transform(it.data)))
                        }
                        is ResultNetwork.Error -> {
                            emit(ResultDomain.Error(it.exception.toString()))
                        }
                    }
                }
            } catch (ex: Exception) {
                return@flow emit(ResultDomain.Error(ex.toString()))
            }
        }
    }
}