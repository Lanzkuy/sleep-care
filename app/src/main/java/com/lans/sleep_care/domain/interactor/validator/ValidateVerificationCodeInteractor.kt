package com.lans.sleep_care.domain.interactor.validator

import com.lans.sleep_care.domain.model.ValidationResult
import com.lans.sleep_care.domain.usecase.validator.ValidateVerificationCodeUseCase
import java.util.regex.Pattern

class ValidateVerificationCodeInteractor: ValidateVerificationCodeUseCase {
    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Verification code must be filled"
            )
        }

        if (!Pattern.matches("\\d{5}", input)) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Verification code must be a 5-digit number"
            )
        }

        return ValidationResult(
            isSuccess = true,
            errorMessage = null
        )
    }
}