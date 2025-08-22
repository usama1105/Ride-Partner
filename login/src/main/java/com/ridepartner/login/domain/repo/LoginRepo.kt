package com.ridepartner.login.domain.repo

import com.example.network.utils.Resource
import com.ridepartner.login.domain.request.LoginRequest
import com.ridepartner.login.domain.response.LoginResponse

internal interface LoginRepo {
    suspend fun login(request: LoginRequest): Resource<LoginResponse>
}