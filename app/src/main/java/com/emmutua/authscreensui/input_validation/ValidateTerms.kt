package com.emmutua.authscreensui.input_validation

import com.emmutua.authscreensui.R

class ValidateTerms {
    fun execute(acceptedTerms : Boolean) : ValidationResult{
        if (!acceptedTerms){
            return ValidationResult(
                successful = false,
                errorMessage = R.string.check_accepted_terms_msg
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}