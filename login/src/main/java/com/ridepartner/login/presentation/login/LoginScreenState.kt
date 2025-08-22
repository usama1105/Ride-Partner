package com.ridepartner.login.presentation.login

data class LoginScreenState(
    val email: String = "malaika",
    val password: String = "12345678",
    val isLoading: Boolean = false,
    val error: String? = null
)