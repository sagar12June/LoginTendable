package com.sagar.tendable.repository

import com.sagar.tendable.retrofit.LoginService
import com.sagar.tendable.util.ApiException
import com.sagar.tendable.util.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginServiceRepo constructor(private val loginService: LoginService) {

    fun saveLoginData(loginReq : String) : Flow<ApiResponse<String>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val responseData = loginService.saveLoginData(loginReq)
                emit(ApiResponse.Success(data = responseData.toString()))
            } catch (e: Exception) { emit(ApiException.resolveError(e)) }

        }.flowOn(Dispatchers.IO)
    }
}