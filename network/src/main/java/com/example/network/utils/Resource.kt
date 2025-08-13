package com.example.network.utils

import com.example.network.utils.errors.ErrorModel

sealed class Resource<out T> {
    data class Valid<T>(val data: T) : Resource<T>()
    data class Invalid(val errorModel: ErrorModel) : Resource<Nothing>()
}

suspend fun <T> Resource<T>.check(
    success: suspend (T) -> Unit,
    failure: suspend (error: ErrorModel) -> Unit
) {
    when (this) {
        is Resource.Invalid -> failure(this.errorModel)
        is Resource.Valid -> success(this.data)
    }
}
