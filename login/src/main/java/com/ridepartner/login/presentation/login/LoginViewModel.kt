package com.ridepartner.login.presentation.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel:ViewModel() {
    val email = mutableStateOf("")
    val password = mutableStateOf("")

    fun onLoginClick(){
        println("Email: ${email.value}, Password: ${password.value}")

        Log.d("check","${email.value}, password : ${password.value}")
    }

}