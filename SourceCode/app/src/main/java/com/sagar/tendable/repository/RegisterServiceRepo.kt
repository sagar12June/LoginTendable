package com.sagar.tendable.repository

import com.sagar.tendable.retrofit.RegisterService
import com.sagar.tendable.util.ApiException
import com.sagar.tendable.util.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RegisterServiceRepo constructor(private val registerService: RegisterService){

    fun saveRegisterData(registerReq : String): Flow<ApiResponse<String>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val responseData = registerService.saveRegisterData(registerReq)
                emit(ApiResponse.Success(data = responseData.toString()))
            } catch (e: Exception) {
                emit(ApiException.resolveError(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}