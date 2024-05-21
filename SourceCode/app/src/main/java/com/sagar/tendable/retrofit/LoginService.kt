package com.sagar.tendable.retrofit

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {

    //login
    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun saveLoginData(@Body body : String)

}