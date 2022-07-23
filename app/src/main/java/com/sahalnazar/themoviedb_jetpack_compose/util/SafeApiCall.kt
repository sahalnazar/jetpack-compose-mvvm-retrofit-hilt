package com.sahalnazar.themoviedb_jetpack_compose.util

import android.util.Log
import retrofit2.Response
import java.net.UnknownHostException

const val ERROR_NO_INTERNET = "Please check your internet connection"
const val ERROR_UNKNOWN = "An unknown error has occurred"

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T?, val code: Int) : ResultWrapper<T>()
    data class Failure<out T>(val message: String, val data: T?, val code: Int) : ResultWrapper<T>()
}

inline fun <reified T> safeApiCall(
    apiCall: () -> Response<T>
): ResultWrapper<T> {
    var responseCode = 0
    return try {
        val response = apiCall.invoke()
        responseCode = response.code()
        Log.d("safeApiCall.response", "response: $response")
        when {
            response.isSuccessful -> {
                if (response.body() == null) {
                    ResultWrapper.Success(null, response.code())
                } else {
                    ResultWrapper.Success(response.body(), response.code())
                }
            }
            else -> {
                ResultWrapper.Failure(response.message(), response.body(), response.code())
            }
        }
    } catch (throwable: Exception) {
        Log.e("safeApiCall", "throwable: $throwable")
        when (throwable) {
            is UnknownHostException -> ResultWrapper.Failure(ERROR_NO_INTERNET, null, 0)
            else -> ResultWrapper.Failure(ERROR_UNKNOWN, null, responseCode)
        }
    }
}