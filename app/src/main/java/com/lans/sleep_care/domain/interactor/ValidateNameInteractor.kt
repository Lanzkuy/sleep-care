package com.lans.sleep_care.domain.interactor

import com.lans.instagram_clone.domain.model.ValidationResult
import com.lans.sleep_care.domain.usecase.ValidateNameUseCase

class ValidateNameInteractor : ValidateNameUseCase {
    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Name must be filled"
            )
        }

        if (input.length < 4) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Name must be at least 4 characters long"
            )
        }

        return ValidationResult(
            isSuccess = true
        )
    }
}