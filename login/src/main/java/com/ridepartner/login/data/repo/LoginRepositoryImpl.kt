package com.ridepartner.login.data.repo

import com.example.network.utils.Resource
import com.example.network.utils.performPostRequest
import com.ridepartner.login.domain.repo.LoginRepo
import com.ridepartner.login.domain.request.LoginRequest
import com.ridepartner.login.domain.response.LoginResponse
import com.ridepartner.login.utils.APINames

internal class LoginRepositoryImpl : LoginRepo {
    override suspend fun login(request: LoginRequest): Resource<LoginResponse> {
        return performPostRequest<LoginRequest, LoginResponse>(
            apiName = APINames.LOGIN,
            model = request
        )
    }
}
