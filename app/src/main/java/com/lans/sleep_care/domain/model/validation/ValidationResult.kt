package com.lans.sleep_care.domain.model.validation

data class ValidationResult(
    val isSuccess: Boolean,
    val errorMessage: String? = null
)
