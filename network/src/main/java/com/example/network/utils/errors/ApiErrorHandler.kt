package com.example.network.utils.errors

import android.util.Log
import com.example.network.utils.Resource
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ApiErrorHandler {
    private const val genericNetworkError = "An error occurred getting data from server."

    fun getNetworkErrorMessage(e: Exception) = when (e) {
        is SocketTimeoutException -> "Request timed out. Try again."
        is UnknownHostException -> "Unable to connect to server."
        is IOException -> "Network not available."
        else -> genericNetworkError
    }

    fun <T> getErrorMessage(response: Response<T>): ErrorModel {
        return response.errorBody()?.run {
            JSONObject(string()).run {
                ErrorModel(
//                    status = getString("status") ?: "",
                    description = getString("message") ?: "",
                    code = getString("code") ?: "",
                )
            }
        } ?: ErrorModel(
            description = "A server error occurred.",
            code = response.code().toString()
        )
    }

    fun getUnknownExceptionMessage() = genericNetworkError


    inline fun <reified T> getInvalid(response: Response<JsonObject>?): Resource<T> {
        if (response?.errorBody() != null && response.errorBody()?.string() != null) {
            val body = response.errorBody()?.string()
            Log.e("ApiErrorHandler", body ?: "Null errorBody()")


            return Resource.Invalid(errorModel = ErrorModel(description = "An error occurred getting data from server."))
        } else {
            return Resource.Invalid(errorModel = ErrorModel(description = "An error occurred getting data from server."))
        }
    }
}