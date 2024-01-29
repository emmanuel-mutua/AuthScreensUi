package com.emmutua.authscreensui.input_validation

import android.util.Patterns
import com.emmutua.authscreensui.R

class ValidateEmail {
    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.email_blank_msg
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult(
                successful = false,
                errorMessage = R.string.email_blank_msg
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}