package com.sagar.tendable.util

import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ApiException {

    companion object {
        fun resolveError(e: Exception): ApiResponse.Error {
            var error = e
            when (e) {
                is SocketTimeoutException ->
                    error = NetworkErrorException(errorMessage = "Connection error!")
                is ConnectException ->
                    error = NetworkErrorException(errorMessage = "No internet access!")
                is UnknownHostException ->
                    error = NetworkErrorException(errorMessage = "No internet access!")
                is HttpException -> {
                    error = when (e.code()) {
                        400 -> NetworkErrorException(e.code(), "Registration was unsuccessful due to one or both fields missing from the JSON!")
                        502 -> NetworkErrorException(e.code(), "Internal error!")
                        401 -> NetworkErrorException(e.code(), "User already exists!")
                        else -> NetworkErrorException.parseException(e) //400
                    }
                }
            }
            return ApiResponse.Error(error)
        }
    }

    open class NetworkErrorException(
        val errorCode: Int = -1,
        val errorMessage: String,
        val response: String = ""
    ) : Exception() {
        override val message: String
            get() = localizedMessage

        override fun getLocalizedMessage(): String {
            return errorMessage
        }

        companion object {
            fun parseException(e: HttpException): NetworkErrorException {
                val errorBody = e.response()?.errorBody()?.string()

                return try {//here you can parse the error body that comes from server
                    NetworkErrorException(
                        e.code(),
                        JSONObject(errorBody!!).getString("message")
                    )
                } catch (_: Exception) {
                    NetworkErrorException(e.code(), "Unexpected error!!ً")
                }
            }
        }
    }

    class AuthenticationException(authMessage: String) :
        NetworkErrorException(errorMessage = authMessage)
}