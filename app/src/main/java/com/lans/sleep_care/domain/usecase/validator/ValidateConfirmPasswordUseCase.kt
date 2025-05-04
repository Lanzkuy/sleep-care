package com.lans.sleep_care.domain.usecase.validator

import com.lans.instagram_clone.domain.model.ValidationResult

interface ValidateConfirmPasswordUseCase {
    fun execute(password: String, confirmPassword: String): ValidationResult
}