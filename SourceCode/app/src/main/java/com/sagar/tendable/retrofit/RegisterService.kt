package com.sagar.tendable.retrofit

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RegisterService {

    //register
    @Headers("Content-Type: application/json")
    @POST("register")
    suspend fun saveRegisterData(@Body body : String)
}