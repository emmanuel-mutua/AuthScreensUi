package com.emmutua.authscreensui.input_validation

import com.emmutua.authscreensui.R

class ValidatePassword {
    fun execute(password: String): ValidationResult {
        if (password.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = R.string.password_field_blank_msg
            )
        }
        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.password_length_short_msg
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}