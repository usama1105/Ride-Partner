package com.example.ridepartner.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ridepartner.login.navigation.LoginScreens
import com.ridepartner.login.navigation.loginNavigationGraph

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: Any = LoginScreens.AppEntryPoint
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        loginNavigationGraph(navController = navController)
    }
}

