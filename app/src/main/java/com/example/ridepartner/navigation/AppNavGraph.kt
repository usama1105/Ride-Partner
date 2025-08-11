package com.example.ridepartner.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

import com.ridepartner.login.navigation.LoginScreens
import com.ridepartner.login.navigation.loginNavigationGraph

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: Any = LoginScreens.Login // Keep as Login
) {
    NavHost(
        navController,
        startDestination = startDestination
    ) {
        loginNavigationGraph(navController)
    }
}