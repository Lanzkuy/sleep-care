package com.lans.sleep_care.domain.interactor.validator

import com.lans.sleep_care.domain.model.validation.ValidationResult
import com.lans.sleep_care.domain.usecase.validator.ValidateCurrentPasswordUseCase

class ValidateCurrentPasswordInteractor : ValidateCurrentPasswordUseCase {
    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Current password must be filled"
            )
        }

        return ValidationResult(
            isSuccess = true,
            errorMessage = null
        )
    }
}