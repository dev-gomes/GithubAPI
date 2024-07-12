package com.example.lib_domain

sealed class ResultType<out T> {
    data class Success<out T>(val data: T) : ResultType<T>()
    data class Error(val exception: Throwable) : ResultType<Nothing>()
}

fun <T : Any, R : Any> ResultType<T>.asResult(body: (T) -> R): ResultType<R> =
    when (this) {
        is ResultType.Success -> ResultType.Success(body(data))
        is ResultType.Error -> ResultType.Error(this.exception)
    }

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultType<T> {
    return try {
        ResultType.Success(apiCall())
    } catch (e: Exception) {
        ResultType.Error(e)
    }
}