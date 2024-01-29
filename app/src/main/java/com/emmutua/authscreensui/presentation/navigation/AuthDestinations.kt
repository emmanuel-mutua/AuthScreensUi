package com.emmutua.authscreensui.presentation.navigation

sealed class AuthDestinations(val route : String) {
    data object CreateAccountScreen: AuthDestinations(route = "create_account")
    data object SignInScreen: AuthDestinations(route = "sign_in")
}