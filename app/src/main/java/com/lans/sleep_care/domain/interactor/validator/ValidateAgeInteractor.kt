package com.lans.sleep_care.domain.interactor.validator

import com.lans.instagram_clone.domain.model.ValidationResult
import com.lans.sleep_care.domain.usecase.validator.ValidateAgeUseCase

class ValidateAgeInteractor : ValidateAgeUseCase {
    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Age must be filled"
            )
        }

        if (!input.all { it.isDigit() }) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Age must be a number"
            )
        }

        return ValidationResult(
            isSuccess = true,
            errorMessage = null
        )
    }
}