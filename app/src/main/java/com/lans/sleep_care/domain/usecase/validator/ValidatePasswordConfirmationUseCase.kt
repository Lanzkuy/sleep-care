package com.lans.sleep_care.domain.usecase.validator

import com.lans.sleep_care.domain.model.validation.ValidationResult

interface ValidatePasswordConfirmationUseCase {
    fun execute(password: String, passwordConfirmation: String): ValidationResult
}