package com.lans.sleep_care.domain.interactor.validator

import com.lans.sleep_care.domain.usecase.validator.ValidateAgeUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidatePasswordConfirmationUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidateEmailUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidateNameUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidatePasswordUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidateVerificationCodeUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidatorUseCase
import javax.inject.Inject

class ValidatorInteractor @Inject constructor(
    override val email: ValidateEmailUseCase,
    override val name: ValidateNameUseCase,
    override val password: ValidatePasswordUseCase,
    override val passwordConfirmation: ValidatePasswordConfirmationUseCase,
    override val verificationCode: ValidateVerificationCodeUseCase,
    override val age: ValidateAgeUseCase,
) : ValidatorUseCase