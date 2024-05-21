package com.sagar.tendable.util

sealed class ApiResponse<out T> {
    object Empty : ApiResponse<Nothing>()
    object Loading : ApiResponse<Nothing>()
    data class Error(val exception: Throwable) : ApiResponse<Nothing>()
    data class Success<T>(val data: T) : ApiResponse<T>()
}
