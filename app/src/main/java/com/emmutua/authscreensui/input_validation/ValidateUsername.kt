package com.emmutua.authscreensui.input_validation

import com.emmutua.authscreensui.R

class ValidateUsername {
    fun execute(username : String): ValidationResult{
        if (username.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = R.string.username_empty_msg
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}