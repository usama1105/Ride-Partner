package com.ridepartner.login.domain.request

import com.google.gson.annotations.SerializedName

internal data class LoginRequest (
    @SerializedName("email") val email: String = "",
    @SerializedName("password") val password: String = "",
)