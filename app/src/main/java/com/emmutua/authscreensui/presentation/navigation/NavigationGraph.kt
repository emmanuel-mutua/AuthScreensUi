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

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    val createAccountViewModel: CreateAccountViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = AuthDestinations.CreateAccountScreen.route
    ) {
        composable(AuthDestinations.CreateAccountScreen.route) {
            CreateAccountScreen(
                uiState = createAccountViewModel.uiState,
                onEvent = {
                    createAccountViewModel.onEvent(it)
                }
            )
        }
        composable(AuthDestinations.SignInScreen.route) {
            SignInScreen()
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