package com.emmutua.authscreensui.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun InputField(
    value: String,
    label : String,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = Modifier.padding(top = 10.dp),
        value = value, onValueChange = onValueChange,
        label = { Text(text = label)},
        maxLines = 1
    )
}

@Composable
fun PasswordInputField(
    value: String,
    passwordVisibilityIcon: ImageVector,
    onVisibilityIconClick: () -> Unit,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = Modifier.padding(top = 10.dp),
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        trailingIcon = {
            IconButton(onClick = onVisibilityIconClick) {
                Icon(imageVector = passwordVisibilityIcon, contentDescription = "")
            }
        }
    )
}