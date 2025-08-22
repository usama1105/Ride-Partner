package com.ridepartner.login.navigation

import kotlinx.serialization.Serializable


sealed interface LoginScreens {

    @Serializable
    data object AppEntryPoint

    @Serializable
    data object Login : LoginScreens

    @Serializable
    data object Signup : LoginScreens

    @Serializable
    data object Dashboard : LoginScreens
}