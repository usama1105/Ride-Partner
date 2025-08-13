package com.example.network.utils.errors

data class ErrorModel (
    val description : String,
    val title: String = "Error",
    val code : String? = null,
    val responseCode: Int? = 0,
)