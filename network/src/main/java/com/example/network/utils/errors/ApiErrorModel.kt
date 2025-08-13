package com.example.network.utils.errors

import com.google.gson.annotations.SerializedName

data class ApiErrorModel(
//    @SerializedName("status") val status: Boolean?,
    @SerializedName("message") val message: Message
) {

    data class Message(
        @SerializedName("code") val code: String,
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("message") val message: String,
    )
}

fun ApiErrorModel?.toErrorModel(responseCode: Int?, description: String = "Some error occurred.") = ErrorModel(
    title = this?.message?.title ?: "Error",
    description = this?.message?.description ?: this?.message?.message ?: description,
    code = this?.message?.code,
    responseCode = responseCode
)