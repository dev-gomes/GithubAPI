package com.example.lib_domain

import retrofit2.Response

sealed class ResultType<out T> {
    data class Success<out T>(val data: T) : ResultType<T>()
    data class Error(val exception: Throwable) : ResultType<Nothing>()
}

fun <T : Any, R : Any> ResultType<T>.asResult(body: (T) -> R): ResultType<R> =
    when (this) {
        is ResultType.Success -> ResultType.Success(body(data))
        is ResultType.Error -> ResultType.Error(this.exception)
    }

suspend fun <T:Any> safeApiCall(apiCall: suspend () -> Response<T>): ResultType<T> {
    return try {
        apiCall().toResult()
    } catch (e: Exception) {
        ResultType.Error(e)
    }
}

private fun <T : Any> Response<T>.toResult(): ResultType<T> {
    return when {
        isSuccessful -> body()?.let {
            ResultType.Success(it)
        } ?: ResultType.Error(IllegalStateException())

        else -> ResultType.Error(IllegalStateException())
    }
}
