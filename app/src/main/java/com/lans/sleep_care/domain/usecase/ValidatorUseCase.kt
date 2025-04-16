package com.lans.sleep_care.domain.usecase

interface ValidatorUseCase {
    val email: ValidateEmailUseCase
    val name: ValidateNameUseCase
    val password: ValidatePasswordUseCase
    val verificationCode: ValidateVerificationCodeUseCase
}