package com.lans.sleep_care.domain.interactor.validator

import com.lans.sleep_care.domain.model.ValidationResult
import com.lans.sleep_care.domain.usecase.validator.ValidateConfirmPasswordUseCase

class ValidateConfirmPasswordInteractor: ValidateConfirmPasswordUseCase {
    override fun execute(password: String, confirmPassword:String): ValidationResult {
        if (confirmPassword.isBlank()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Confirm password must be filled"
            )
        }

        if (confirmPassword != password) {
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