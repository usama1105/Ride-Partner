package com.ridepartner.login.presentation.login

data class LoginScreenState(
    val email: String = "usama_javaid@yopmail.com",
    val password: String = "Qwerty@10",
    val isLoading: Boolean = false,
    val error: String? = null
)