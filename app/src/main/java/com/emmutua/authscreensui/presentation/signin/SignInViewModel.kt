package com.emmutua.authscreensui.presentation.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emmutua.authscreensui.input_validation.ValidatePassword
import com.emmutua.authscreensui.input_validation.ValidateUsername
import com.emmutua.authscreensui.input_validation.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    private val validateUsername: ValidateUsername = ValidateUsername(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
):ViewModel() {
    var uiState by mutableStateOf(SignInUiState())
    private var _signInEventsChannel = Channel<ValidationEvent>()
    val signInEventsChannel = _signInEventsChannel.receiveAsFlow()

    fun onEvent(event: SignInEvent){
        when(event){
            is SignInEvent.ChangedUsername -> {
                uiState = uiState.copy(username = event.username)
            }
            is SignInEvent.ChangedPassword -> {
                uiState = uiState.copy(password = event.password)
            }
            is SignInEvent.CheckRememberMe -> {
                uiState = uiState.copy(checkedRememberMe = event.checked)
            }
            is SignInEvent.OnPasswordVisibilityClick -> {
                uiState = uiState.copy(passwordVisible = event.visible)
            }

            is SignInEvent.SignIn -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val validateUsernameResult = validateUsername.execute(uiState.username)
        val validatePasswordResult = validatePassword.execute(uiState.password)

        val hasError = listOf(
            validatePasswordResult,
            validateUsernameResult
        ).any { !it.successful }

        if (hasError){
            uiState = uiState.copy(
                usernameError = validateUsernameResult.errorMessage.toString(),
                passwordError = validatePasswordResult.errorMessage.toString()
            )
        }
        viewModelScope.launch {
            _signInEventsChannel.send(ValidationEvent.Success)
        }
    }
}

data class SignInUiState(
    val username: String = "",
    val usernameError: String? = null,
    val password : String = "",
    val passwordError : String? = null,
    val passwordVisible :Boolean = false,
    val checkedRememberMe : Boolean = false
)