package com.lans.sleep_care.domain.usecase.validator

import com.lans.sleep_care.domain.model.ValidationResult

interface ValidateNameUseCase {
    fun execute(input: String): ValidationResult
}