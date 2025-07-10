package com.lans.sleep_care.domain.usecase.validator

import com.lans.sleep_care.domain.model.validation.ValidationResult

interface ValidateCurrentPasswordUseCase {
    fun execute(input: String): ValidationResult
}