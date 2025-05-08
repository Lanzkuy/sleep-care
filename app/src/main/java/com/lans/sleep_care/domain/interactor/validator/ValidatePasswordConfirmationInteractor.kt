package com.lans.sleep_care.domain.interactor.validator

import com.lans.sleep_care.domain.model.ValidationResult
import com.lans.sleep_care.domain.usecase.validator.ValidatePasswordConfirmationUseCase

class ValidatePasswordConfirmationInteractor: ValidatePasswordConfirmationUseCase {
    override fun execute(password: String, passwordConfirmation:String): ValidationResult {
        if (passwordConfirmation.isBlank()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Password confirmation must be filled"
            )
        }

        if (passwordConfirmation != password) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Password confirmation does not match"
            )
        }

        return ValidationResult(
            isSuccess = true,
            errorMessage = null
        )
    }
}