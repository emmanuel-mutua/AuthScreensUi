package com.emmutua.authscreensui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.emmutua.authscreensui.presentation.register.CreateAccountScreen
import com.emmutua.authscreensui.presentation.register.CreateAccountViewModel
import com.emmutua.authscreensui.presentation.signin.SignInScreen
import com.emmutua.authscreensui.presentation.signin.SignInViewModel

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    val createAccountViewModel: CreateAccountViewModel = viewModel()
    val signInViewModel: SignInViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = AuthDestinations.CreateAccountScreen.route
    ) {
        composable(AuthDestinations.CreateAccountScreen.route) {
            CreateAccountScreen(
                uiState = createAccountViewModel.uiState,
                viewModel = createAccountViewModel,
                onEvent = {
                    createAccountViewModel.onEvent(it)
                },
                onSignButtonClick = {
                    navController.navigateWithPop(AuthDestinations.SignInScreen.route)
                },
                navigateToLogin = {
                    navController.navigateWithPop(AuthDestinations.SignInScreen.route)
                }
            )
        }
        composable(AuthDestinations.SignInScreen.route) {
            SignInScreen(
                uiState = signInViewModel.uiState,
                viewModel = signInViewModel,
                onEvent = { signInViewModel.onEvent(it) },
                onSignUpClick = { navController.navigateWithPop(AuthDestinations.CreateAccountScreen.route) })
        }
    }
}

fun NavController.navigateWithPop(route: String) {
    navigate(route = route) {
        popUpTo(AuthDestinations.CreateAccountScreen.route) {
            saveState = true
        }
        restoreState = true
    }
}