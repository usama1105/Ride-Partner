package com.ridepartner.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ridepartner.login.presentation.dashboard.DashboardFragment
import com.ridepartner.login.presentation.login.LoginFragment
import com.ridepartner.login.presentation.login.LoginViewModel
import com.ridepartner.login.presentation.signup.SignupScreen
import org.koin.androidx.compose.koinViewModel


fun NavGraphBuilder.loginNavigationGraph(
    navController: NavHostController
) {
    navigation<LoginScreens.AppEntryPoint>(startDestination = LoginScreens.Login) {
        composable<LoginScreens.Login> {
            val viewModel: LoginViewModel = koinViewModel()
            LoginFragment(
                state = viewModel.state,
                onLoginClick = {
                    viewModel.login()
                }

            )

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