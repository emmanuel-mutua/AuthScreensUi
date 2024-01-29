package com.emmutua.authscreensui.input_validation

sealed class ValidationEvent {
    data object Success : ValidationEvent()
}