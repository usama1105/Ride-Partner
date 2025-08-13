package com.example.network.logs

data class ApiLog(
    val apiName: String,
    val url: String,
    val request: String,
    val response: String,
    val responseCode: Int
)  {
    val isSuccess get() = responseCode == 200
}