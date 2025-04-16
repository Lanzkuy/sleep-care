package com.lans.sleep_care.di

import com.lans.sleep_care.domain.interactor.ValidatorInteractor
import com.lans.sleep_care.domain.interactor.ValidateEmailInteractor
import com.lans.sleep_care.domain.interactor.ValidateNameInteractor
import com.lans.sleep_care.domain.interactor.ValidatePasswordInteractor
import com.lans.sleep_care.domain.interactor.ValidateVerificationCodeInteractor
import com.lans.sleep_care.domain.usecase.ValidateEmailUseCase
import com.lans.sleep_care.domain.usecase.ValidateNameUseCase
import com.lans.sleep_care.domain.usecase.ValidatePasswordUseCase
import com.lans.sleep_care.domain.usecase.ValidateVerificationCodeUseCase
import com.lans.sleep_care.domain.usecase.ValidatorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideValidatorUseCase(
        validateEmailUseCase: ValidateEmailUseCase,
        validateNameUseCase: ValidateNameUseCase,
        validatePasswordUseCase: ValidatePasswordUseCase,
        validateVerificationCodeUseCase: ValidateVerificationCodeUseCase,
    ): ValidatorUseCase {
        return ValidatorInteractor(
            validateEmailUseCase,
            validateNameUseCase,
            validatePasswordUseCase,
            validateVerificationCodeUseCase
        )
    }

    @Provides
    @Singleton
    fun provideValidateEmailUseCase(): ValidateEmailUseCase {
        return ValidateEmailInteractor()
    }

    @Provides
    @Singleton
    fun provideValidateNameUseCase(): ValidateNameUseCase {
        return ValidateNameInteractor()
    }

    @Provides
    @Singleton
    fun provideValidatePasswordUseCase(): ValidatePasswordUseCase {
        return ValidatePasswordInteractor()
    }

    @Provides
    @Singleton
    fun provideValidateVerificationCodeUseCase(): ValidateVerificationCodeUseCase {
        return ValidateVerificationCodeInteractor()
    }
}