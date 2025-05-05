package com.lans.sleep_care.domain.model

data class ValidationResult(
    val isSuccess: Boolean,
    val errorMessage: String? = null
)
