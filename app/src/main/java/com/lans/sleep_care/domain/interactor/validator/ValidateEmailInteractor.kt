package com.lans.sleep_care.domain.interactor.validator

import android.util.Patterns
import com.lans.sleep_care.domain.model.validation.ValidationResult
import com.lans.sleep_care.domain.usecase.validator.ValidateEmailUseCase

class ValidateEmailInteractor : ValidateEmailUseCase {
    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Email must be filled"
            )
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Email address is not valid"
            )
        }

        return ValidationResult(
            isSuccess = true
        )
    }
}