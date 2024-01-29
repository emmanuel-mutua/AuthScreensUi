package com.emmutua.authscreensui.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.emmutua.authscreensui.R
import com.emmutua.authscreensui.presentation.components.InputField

@Composable
fun CreateAccountScreen(
    uiState: CreateAccountUiState,
    onEvent: (CreateAccountEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp, start = 10.dp, end = 10.dp)
    ) {
        Text(text = R.string.username_text.toString())
        InputField(
            value = uiState.username,
            label = R.string.username_label.toString(),
            onValueChange = { onEvent(CreateAccountEvent.UserNameChanged(it))}
        )
        Text(text = R.string.email_text.toString())
        InputField(
            value = uiState.username,
            label = R.string.email_label.toString(),
            onValueChange = { onEvent(CreateAccountEvent.UserNameChanged(it))}
        )
    }
}