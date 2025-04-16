package com.lans.sleep_care.domain.usecase

import com.lans.instagram_clone.domain.model.ValidationResult

interface ValidateVerificationCodeUseCase {
    fun execute(input: String): ValidationResult
}