package com.sagar.tendable.di

import com.sagar.tendable.retrofit.LoginService
import com.sagar.tendable.retrofit.RegisterService
import com.sagar.tendable.util.Constants.Companion.timeOutInSec
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single{
        val okHttpClient = OkHttpClient.Builder() //singleton obj of okHttpClient
            .addInterceptor(TokenInterceptor())
            .readTimeout(timeOutInSec, TimeUnit.SECONDS)
            .connectTimeout(timeOutInSec, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder() //singleton obj of retrofit
            .baseUrl("http://localhost:5001/api/")
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single {
        val retrofit = get<Retrofit>()
		retrofit.create(LoginService::class.java) //singleton obj of ApiService
    }
    single {
        val retrofit = get<Retrofit>()
        retrofit.create(RegisterService::class.java)
    }
}