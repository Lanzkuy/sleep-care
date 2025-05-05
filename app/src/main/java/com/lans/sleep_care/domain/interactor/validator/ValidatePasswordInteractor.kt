package com.lans.sleep_care.domain.interactor.validator

import com.lans.sleep_care.domain.model.ValidationResult
import com.lans.sleep_care.domain.usecase.validator.ValidatePasswordUseCase
import java.util.regex.Pattern

class ValidatePasswordInteractor : ValidatePasswordUseCase {
    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Password must be filled"
            )
        }

        if (input.length < 8) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Password must be at least 8 characters long"
            )
        }

        if (Pattern.matches("[a-zA-Z0-9]", input)) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Please include at least one letter and one number in your password"
            )
        }

        return ValidationResult(
            isSuccess = true,
            errorMessage = null
        )
    }
}