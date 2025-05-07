package com.lans.sleep_care.di

import com.lans.sleep_care.domain.interactor.auth.IsAuthenticatedInteractor
import com.lans.sleep_care.domain.interactor.auth.LoginInteractor
import com.lans.sleep_care.domain.interactor.auth.LogoutInteractor
import com.lans.sleep_care.domain.interactor.auth.OtpRequestInteractor
import com.lans.sleep_care.domain.interactor.auth.RegisterInteractor
import com.lans.sleep_care.domain.interactor.auth.StoreSessionInteractor
import com.lans.sleep_care.domain.interactor.auth.VerifyOtpInteractor
import com.lans.sleep_care.domain.interactor.chatbot.GetChatBotAnswerInteractor
import com.lans.sleep_care.domain.interactor.chatbot.GetChatBotHistoryInteractor
import com.lans.sleep_care.domain.interactor.chatbot.StoreChatBotHistoryInteractor
import com.lans.sleep_care.domain.interactor.user.GetMeInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidateAgeInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidateConfirmPasswordInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidateEmailInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidateNameInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidatePasswordInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidateVerificationCodeInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidatorInteractor
import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.repository.IChatBotRepository
import com.lans.sleep_care.domain.repository.IUserRepository
import com.lans.sleep_care.domain.usecase.auth.IsAuthenticatedUseCase
import com.lans.sleep_care.domain.usecase.auth.LoginUseCase
import com.lans.sleep_care.domain.usecase.auth.LogoutUseCase
import com.lans.sleep_care.domain.usecase.auth.OtpRequestUseCase
import com.lans.sleep_care.domain.usecase.auth.RegisterUseCase
import com.lans.sleep_care.domain.usecase.auth.StoreSessionUseCase
import com.lans.sleep_care.domain.usecase.auth.VerifyOtpUseCase
import com.lans.sleep_care.domain.usecase.chatbot.GetChatBotAnswerUseCase
import com.lans.sleep_care.domain.usecase.chatbot.GetChatBotHistoryUseCase
import com.lans.sleep_care.domain.usecase.chatbot.StoreChatBotHistoryUseCase
import com.lans.sleep_care.domain.usecase.user.GetMeUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidateAgeUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidateConfirmPasswordUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidateEmailUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidateNameUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidatePasswordUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidateVerificationCodeUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidatorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideIsAuthenticatedUseCase(
        repository: IAuthRepository
    ): IsAuthenticatedUseCase {
        return IsAuthenticatedInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideStoreSessionUseCase(
        repository: IAuthRepository
    ): StoreSessionUseCase {
        return StoreSessionInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(
        repository: IAuthRepository
    ): LoginUseCase {
        return LoginInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(
        repository: IAuthRepository
    ): RegisterUseCase {
        return RegisterInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(
        repository: IAuthRepository
    ): LogoutUseCase {
        return LogoutInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideOtpRequestUseCase(
        repository: IAuthRepository
    ): OtpRequestUseCase {
        return OtpRequestInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideVerifyOtpUseCase(
        repository: IAuthRepository
    ): VerifyOtpUseCase {
        return VerifyOtpInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideGetMeUseCase(
        repository: IUserRepository
    ): GetMeUseCase {
        return GetMeInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideGetChatBotAnswerUseCase(
        repository: IChatBotRepository
    ): GetChatBotAnswerUseCase {
        return GetChatBotAnswerInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideStoreChatBotHistoryUseCase(
        repository: IChatBotRepository
    ): StoreChatBotHistoryUseCase {
        return StoreChatBotHistoryInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideGetChatBotHistoryUseCase(
        repository: IChatBotRepository
    ): GetChatBotHistoryUseCase {
        return GetChatBotHistoryInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideValidatorUseCase(
        validateEmailUseCase: ValidateEmailUseCase,
        validateNameUseCase: ValidateNameUseCase,
        validatePasswordUseCase: ValidatePasswordUseCase,
        validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
        validateVerificationCodeUseCase: ValidateVerificationCodeUseCase,
        validateAgeUseCase: ValidateAgeUseCase
    ): ValidatorUseCase {
        return ValidatorInteractor(
            validateEmailUseCase,
            validateNameUseCase,
            validatePasswordUseCase,
            validateConfirmPasswordUseCase,
            validateVerificationCodeUseCase,
            validateAgeUseCase
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
    fun provideValidateConfirmPasswordUseCase(): ValidateConfirmPasswordUseCase {
        return ValidateConfirmPasswordInteractor()
    }

    @Provides
    @Singleton
    fun provideValidateVerificationCodeUseCase(): ValidateVerificationCodeUseCase {
        return ValidateVerificationCodeInteractor()
    }

    @Provides
    @Singleton
    fun provideValidateAgeUseCase(): ValidateAgeUseCase {
        return ValidateAgeInteractor()
    }
}