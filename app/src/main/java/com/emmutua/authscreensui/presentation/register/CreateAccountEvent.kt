package com.emmutua.authscreensui.presentation.register

sealed class CreateAccountEvent {
    data class UserNameChanged(val username : String) : CreateAccountEvent()
    data class EmailChanged(val email : String) : CreateAccountEvent()
    data class PasswordChanged(val password : String) : CreateAccountEvent()
    data class AcceptTerms(val isAccepted : Boolean) : CreateAccountEvent()
    data class OnPasswordVisibilityClick(val visible : Boolean): CreateAccountEvent()
    data object SignUp : CreateAccountEvent()
}