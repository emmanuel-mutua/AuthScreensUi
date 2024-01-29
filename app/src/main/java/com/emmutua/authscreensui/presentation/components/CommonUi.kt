package com.emmutua.authscreensui.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.emmutua.authscreensui.R
import com.emmutua.authscreensui.ui.theme.CustomButtonShape

@Composable
fun InputFieldContainer(
    value: String,
    fieldLabel: Int,
    labelId: Int,
    hasError: Boolean,
    errorTextId: Int?,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
    ) {
        Text(text = stringResource(id = fieldLabel))
        OutlinedTextField(
            modifier = Modifier
                .padding(bottom = 15.dp)
                .fillMaxWidth(),
            value = value, onValueChange = onValueChange,
            placeholder = { Text(text = stringResource(id = labelId)) },
            shape = CustomButtonShape,
            singleLine = true
        )
        if (hasError) {
            Text(
                text = stringResource(id = errorTextId!!),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    color = MaterialTheme.colorScheme.error
                )
            )
        }
    }
}

@Composable
fun PasswordInputField(
    value: String,
    hasError: Boolean,
    errorTextId: Int?,
    passwordVisibilityIcon: Painter,
    onVisibilityIconClick: () -> Unit,
    passwordVisible: Boolean,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(id = R.string.password_lebel))
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            value = value,
            shape = CustomButtonShape,
            onValueChange = onValueChange,
            singleLine = true,
            placeholder = { Text(text = stringResource(id = R.string.your_pass_label_string)) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = onVisibilityIconClick) {
                    Icon(painter = passwordVisibilityIcon, contentDescription = "")
                }
            },
        )
        if (hasError) {
            Text(
                text = stringResource(id = errorTextId!!),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    color = MaterialTheme.colorScheme.error
                )
            )
        }
    }
}

@Composable
fun MyAnnotatedString(text1: String, text2: String, onClick: () -> Unit) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle()) { append(text = text1) }
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) { append(text2) }
    }
    Text(
        modifier = Modifier
            .padding(top = 10.dp)
            .clickable { onClick.invoke() },
        text = annotatedString,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge
    )
}