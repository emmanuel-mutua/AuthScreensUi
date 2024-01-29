package com.emmutua.authscreensui.presentation.register

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.emmutua.authscreensui.R
import com.emmutua.authscreensui.input_validation.ValidationEvent
import com.emmutua.authscreensui.presentation.components.InputFieldContainer
import com.emmutua.authscreensui.presentation.components.MyAnnotatedString
import com.emmutua.authscreensui.presentation.components.PasswordInputField
import com.emmutua.authscreensui.ui.theme.CustomButtonShape

@Composable
fun CreateAccountScreen(
    uiState: CreateAccountUiState,
    viewModel: CreateAccountViewModel,
    onEvent: (CreateAccountEvent) -> Unit,
    onSignButtonClick: () -> Unit,
    navigateToLogin: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.validationEventsChannel.collect { event ->
            when (event) {
                ValidationEvent.Success -> {
                    Toast.makeText(context, R.string.success_text, Toast.LENGTH_SHORT).show()
                    navigateToLogin.invoke()
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, start = 20.dp, end = 20.dp)
    ) {
        val passwordVisibilityIcon =
            if (uiState.passwordVisible) painterResource(id = R.drawable.visibility_on) else painterResource(
                id = R.drawable.visibility_off
            )
        val acceptTermsIcon =
            if (uiState.acceptedTerms) Icons.Default.CheckCircle else Icons.Outlined.CheckCircle

        Text(
            text = stringResource(id = R.string.create_account_string),
            style = MaterialTheme.typography.displaySmall
        )
        InputFieldContainer(
            value = uiState.username,
            fieldLabel = R.string.username_text,
            labelId = R.string.username_label,
            hasError = uiState.usernameError != null,
            errorTextId = uiState.usernameError,
            onValueChange = { onEvent(CreateAccountEvent.UserNameChanged(it)) }
        )
        InputFieldContainer(
            value = uiState.email,
            fieldLabel = R.string.email_text,
            labelId = R.string.email_label,
            hasError = uiState.emailError != null,
            errorTextId = uiState.emailError,
            onValueChange = { onEvent(CreateAccountEvent.EmailChanged(it)) }
        )
        PasswordInputField(
            value = uiState.password,
            hasError = uiState.passwordError != null,
            errorTextId = uiState.passwordError,
            passwordVisible = uiState.passwordVisible,
            passwordVisibilityIcon = passwordVisibilityIcon,
            onVisibilityIconClick = { onEvent(CreateAccountEvent.OnPasswordVisibilityClick(visible = !uiState.passwordVisible)) },
            onValueChange = { onEvent(CreateAccountEvent.PasswordChanged(it)) }
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onEvent(CreateAccountEvent.AcceptTerms(isAccepted = !uiState.acceptedTerms)) }) {
                Icon(imageVector = acceptTermsIcon, contentDescription = "")
            }
            Text(text = stringResource(id = R.string.accept_terms_text))
        }
        if (uiState.termsError != null) {
            Text(
                text = stringResource(id = uiState.termsError),
                style = TextStyle(color = MaterialTheme.colorScheme.error)
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { onEvent(CreateAccountEvent.SignUp) },
                shape = CustomButtonShape,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.sign_up_text))
            }
            MyAnnotatedString(
                text1 = stringResource(id = R.string.already_have_account_string),
                text2 = stringResource(
                    id = R.string.sign_in_string
                ),
                onClick = onSignButtonClick
            )
        }
    }
}