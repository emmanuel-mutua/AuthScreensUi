package com.emmutua.authscreensui.presentation.signin

sealed class SignInEvent {
    data class ChangedUsername(val username : String): SignInEvent()
    data class ChangedPassword(val password : String): SignInEvent()
    data class CheckRememberMe(val checked : Boolean): SignInEvent()
    data class OnPasswordVisibilityClick(val visible : Boolean): SignInEvent()
    data object SignIn : SignInEvent()
}