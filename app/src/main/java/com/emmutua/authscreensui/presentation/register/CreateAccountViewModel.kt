package com.emmutua.authscreensui.presentation.register

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
                uiState = uiState.copy(username = event.username)
            }

            is CreateAccountEvent.EmailChanged -> {
                uiState = uiState.copy(email = event.email)
            }

            is CreateAccountEvent.PasswordChanged -> {
                uiState = uiState.copy(password = event.password)
            }

            is CreateAccountEvent.AcceptTerms -> {
                uiState = uiState.copy(acceptedTerms = event.isAccepted)
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
                usernameError = usernameValidationResult.errorMessage.toString(),
                emailError = emailValidationResult.errorMessage.toString(),
                passwordError = passwordValidationResult.errorMessage.toString(),
                termsError = termsValidationResult.errorMessage.toString()
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
    val usernameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val acceptedTerms: Boolean = false,
    val termsError: String? = null,
    val passwordVisible : Boolean = false
)
