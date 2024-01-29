package com.emmutua.authscreensui.input_validation

import androidx.annotation.StringRes

data class ValidationResult(
    val successful: Boolean,
    @StringRes val errorMessage: Int? = null
)