package com.jmvincenti.myuserdirectory.apiclient

import com.jmvincenti.myuserdirectory.apiclient.model.ApiError
import com.jmvincenti.myuserdirectory.apiclient.model.ResultWrapper
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): ResultWrapper<T> = try {
    ResultWrapper.Success(apiCall.invoke())
} catch (throwable: Throwable) {
    throwable.printStackTrace()
    when (throwable) {
        is IOException -> ResultWrapper.NetworkError
        is HttpException -> {
            val code = throwable.code()
            val errorResponse = convertErrorBody(throwable)
            ResultWrapper.GenericError(code, errorResponse?.error)
        }
        else -> {
            ResultWrapper.GenericError(null, null)
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ApiError? = try {
    throwable.response()?.errorBody()?.source()?.let {
        val moshiAdapter = Moshi.Builder().build().adapter(ApiError::class.java)
        moshiAdapter.fromJson(it)
    }
} catch (exception: Exception) {
    null
}
