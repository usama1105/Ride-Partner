package com.ridepartner.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ridepartner.login.presentation.dashboard.DashboardFragment
import com.ridepartner.login.presentation.login.LoginFragment
import com.ridepartner.login.presentation.signup.SignupScreen


fun NavGraphBuilder.loginNavigationGraph(
    navController: NavController
) {
    composable<LoginScreens.Login> {
        LoginFragment { route ->
            navController.navigate(route.toString())
        }
    }

    composable<LoginScreens.Signup> {
        SignupScreen { route ->
            navController.navigate(route.toString())
        }
    }

    composable<LoginScreens.Dashboard> {
        DashboardFragment { route ->
            navController.navigate(route.toString())
        }
    }
}