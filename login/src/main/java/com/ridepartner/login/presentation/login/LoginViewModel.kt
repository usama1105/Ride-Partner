package com.ridepartner.login.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.utils.Resource
import com.ridepartner.login.domain.repo.LoginRepo
import com.ridepartner.login.domain.request.LoginRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class LoginViewModel(
    private val loginRepo: LoginRepo
) : ViewModel() {

    var state by mutableStateOf(LoginScreenState())
        private set

    fun actionEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChange -> {
                state = state.copy(email = event.email)
            }

            is LoginEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            is LoginEvent.Login -> {
                login()
            }
        }
    }

    internal fun login() {
        viewModelScope.launch {
            delay(300)
            when (val call = loginRepo.login(
                request = LoginRequest(
                    email = state.email,
                    password = state.password
                )
            )) {
                is Resource.Invalid -> {}
                is Resource.Valid<*> -> {}
            }
        }

    }

    sealed class LoginEvent {
        data class EmailChange(val email: String) : LoginEvent()
        data class PasswordChanged(val password: String) : LoginEvent()
        data object Login : LoginEvent()
    }

}