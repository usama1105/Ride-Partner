package com.example.network.utils.errors

import com.example.network.utils.Resource

object NetworkErrorHandler {
    fun getGenericError(code: Int): Resource.Invalid {
        return Resource.Invalid(
            ErrorModel(
                title = "Error",
                description = "Error in network request",
                responseCode = code
            )
        )
    }
}