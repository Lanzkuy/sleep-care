package com.lans.sleep_care.domain.interactor

import com.lans.sleep_care.domain.usecase.ValidateEmailUseCase
import com.lans.sleep_care.domain.usecase.ValidateNameUseCase
import com.lans.sleep_care.domain.usecase.ValidatePasswordUseCase
import com.lans.sleep_care.domain.usecase.ValidateVerificationCodeUseCase
import com.lans.sleep_care.domain.usecase.ValidatorUseCase
import javax.inject.Inject

class ValidatorInteractor @Inject constructor(
    override val email: ValidateEmailUseCase,
    override val name: ValidateNameUseCase,
    override val password: ValidatePasswordUseCase,
    override val verificationCode: ValidateVerificationCodeUseCase,
) : ValidatorUseCase