package com.emmutua.authscreensui.presentation.register

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emmutua.authscreensui.input_validation.ValidateEmail
import com.emmutua.authscreensui.input_validation.ValidatePassword
import com.emmutua.authscreensui.input_validation.ValidateTerms
import com.emmutua.authscreensui.input_validation.ValidateUsername
import com.emmutua.authscreensui.input_validation.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CreateAccountViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateTerms: ValidateTerms = ValidateTerms(),
    private val validateUsername : ValidateUsername = ValidateUsername()
) : ViewModel() {
    var uiState by mutableStateOf(CreateAccountUiState())
    private val _validationEventChannel = Channel<ValidationEvent>()
     val validationEventsChannel = _validationEventChannel.receiveAsFlow()
    fun onEvent(event: CreateAccountEvent) {
        when (event) {
            is CreateAccountEvent.UserNameChanged -> {
                uiState = uiState.copy(username = event.username, usernameError = null)
            }

            is CreateAccountEvent.EmailChanged -> {
                uiState = uiState.copy(email = event.email, emailError = null)
            }

            is CreateAccountEvent.PasswordChanged -> {
                uiState = uiState.copy(password = event.password, passwordError = null)
            }

            is CreateAccountEvent.AcceptTerms -> {
                uiState = uiState.copy(acceptedTerms = event.isAccepted, termsError = null)
            }
            is CreateAccountEvent.OnPasswordVisibilityClick -> {
                uiState = uiState.copy(passwordVisible = event.visible)
            }

            is CreateAccountEvent.SignUp -> {
                submitData()
            }

        }
    }

    private fun submitData() {
        val usernameValidationResult = validateUsername.execute(uiState.username)
        val emailValidationResult = validateEmail.execute(uiState.email)
        val passwordValidationResult = validatePassword.execute(uiState.password)
        val termsValidationResult = validateTerms.execute(uiState.acceptedTerms)

        val hasError = listOf(
            usernameValidationResult,
            emailValidationResult,
            passwordValidationResult,
            termsValidationResult
        ).any { !it.successful }

        if (hasError) {
            uiState = uiState.copy(
                usernameError = usernameValidationResult.errorMessage,
                emailError = emailValidationResult.errorMessage,
                passwordError = passwordValidationResult.errorMessage,
                termsError = termsValidationResult.errorMessage
            )
            return
        }
        viewModelScope.launch {
            _validationEventChannel.send(ValidationEvent.Success)
        }
    }
}

data class CreateAccountUiState(
    val username: String = "",
    @StringRes val usernameError: Int? = null,
    val email: String = "",
    @StringRes val emailError: Int? = null,
    val password: String = "",
    @StringRes val passwordError: Int? = null,
    val acceptedTerms: Boolean = false,
    @StringRes val termsError: Int? = null,
    val passwordVisible : Boolean = false
)
