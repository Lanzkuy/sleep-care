package com.lans.sleep_care.domain.usecase

import com.lans.instagram_clone.domain.model.ValidationResult

interface ValidateNameUseCase {
    fun execute(input: String): ValidationResult
}