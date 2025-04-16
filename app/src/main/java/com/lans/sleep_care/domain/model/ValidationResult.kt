package com.lans.instagram_clone.domain.model

data class ValidationResult(
    val isSuccess: Boolean,
    val errorMessage: String? = null
)
