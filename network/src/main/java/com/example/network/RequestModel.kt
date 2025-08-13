package com.example.network

import com.google.gson.annotations.SerializedName

data class RequestModel(
    @SerializedName("payload")
    var request: String? = null
)