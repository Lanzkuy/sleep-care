package com.lans.sleep_care.di

import com.lans.sleep_care.domain.interactor.auth.CheckAuthStatusInteractor
import com.lans.sleep_care.domain.interactor.auth.ForgotPasswordInteractor
import com.lans.sleep_care.domain.interactor.auth.LoginInteractor
import com.lans.sleep_care.domain.interactor.auth.LogoutInteractor
import com.lans.sleep_care.domain.interactor.auth.RegisterInteractor
import com.lans.sleep_care.domain.interactor.auth.RequestOtpInteractor
import com.lans.sleep_care.domain.interactor.auth.ResetPasswordInteractor
import com.lans.sleep_care.domain.interactor.auth.SaveSessionInteractor
import com.lans.sleep_care.domain.interactor.auth.VerifyOtpInteractor
import com.lans.sleep_care.domain.interactor.chatbot.GetChatBotAnswerInteractor
import com.lans.sleep_care.domain.interactor.chatbot.GetChatBotHistoryInteractor
import com.lans.sleep_care.domain.interactor.chatbot.SaveChatBotHistoryInteractor
import com.lans.sleep_care.domain.interactor.psychologist.GetAllPsychologistInteractor
import com.lans.sleep_care.domain.interactor.psychologist.GetPsychologistInteractor
import com.lans.sleep_care.domain.interactor.therapy.GetChatHistoryInteractor
import com.lans.sleep_care.domain.interactor.therapy.GetActiveActiveTherapyInteractor
import com.lans.sleep_care.domain.interactor.therapy.GetTherapyScheduleInteractor
import com.lans.sleep_care.domain.interactor.therapy.SendChatInteractor
import com.lans.sleep_care.domain.interactor.user.ChangePasswordInteractor
import com.lans.sleep_care.domain.interactor.user.GetUserProfileInteractor
import com.lans.sleep_care.domain.interactor.user.UpdateProfileInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidateAgeInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidateCurrentPasswordInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidateEmailInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidateNameInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidatePasswordConfirmationInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidatePasswordInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidateVerificationCodeInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidatorInteractor
import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.repository.IChatBotRepository
import com.lans.sleep_care.domain.repository.IPsychologistRepository
import com.lans.sleep_care.domain.repository.ITherapyRepository
import com.lans.sleep_care.domain.repository.IUserRepository
import com.lans.sleep_care.domain.usecase.auth.CheckAuthStatusUseCase
import com.lans.sleep_care.domain.usecase.auth.ForgotPasswordUseCase
import com.lans.sleep_care.domain.usecase.auth.LoginUseCase
import com.lans.sleep_care.domain.usecase.auth.LogoutUseCase
import com.lans.sleep_care.domain.usecase.auth.RegisterUseCase
import com.lans.sleep_care.domain.usecase.auth.RequestOtpUseCase
import com.lans.sleep_care.domain.usecase.auth.ResetPasswordUseCase
import com.lans.sleep_care.domain.usecase.auth.SaveSessionUseCase
import com.lans.sleep_care.domain.usecase.auth.VerifyOtpUseCase
import com.lans.sleep_care.domain.usecase.chatbot.GetChatBotAnswerUseCase
import com.lans.sleep_care.domain.usecase.chatbot.GetChatBotHistoryUseCase
import com.lans.sleep_care.domain.usecase.chatbot.SaveChatBotHistoryUseCase
import com.lans.sleep_care.domain.usecase.psychologist.GetAllPsychologistUseCase
import com.lans.sleep_care.domain.usecase.psychologist.GetPsychologistUseCase
import com.lans.sleep_care.domain.usecase.therapy.GetChatHistoryUseCase
import com.lans.sleep_care.domain.usecase.therapy.GetTherapySchedulesUseCase
import com.lans.sleep_care.domain.usecase.therapy.GetActiveTherapyUseCase
import com.lans.sleep_care.domain.usecase.therapy.SendChatUseCase
import com.lans.sleep_care.domain.usecase.user.ChangePasswordUseCase
import com.lans.sleep_care.domain.usecase.user.GetUserProfileUseCase
import com.lans.sleep_care.domain.usecase.user.UpdateProfileUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidateAgeUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidateCurrentPasswordUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidateEmailUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidateNameUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidatePasswordConfirmationUseCase
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
        authRepository: IAuthRepository,
        userRepository: IUserRepository
    ): CheckAuthStatusUseCase {
        return CheckAuthStatusInteractor(
            authRepository,
            userRepository
        )
    }

    @Provides
    @Singleton
    fun provideStoreSessionUseCase(
        repository: IAuthRepository
    ): SaveSessionUseCase {
        return SaveSessionInteractor(repository)
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
    fun provideForgotPasswordUseCase(
        repository: IAuthRepository
    ): ForgotPasswordUseCase {
        return ForgotPasswordInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideResetPasswordUseCase(
        repository: IAuthRepository
    ): ResetPasswordUseCase {
        return ResetPasswordInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideRequestOtpUseCase(
        repository: IAuthRepository
    ): RequestOtpUseCase {
        return RequestOtpInteractor(repository)
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
    ): GetUserProfileUseCase {
        return GetUserProfileInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateProfileUseCase(
        repository: IUserRepository
    ): UpdateProfileUseCase {
        return UpdateProfileInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideChangePasswordUseCase(
        repository: IUserRepository
    ): ChangePasswordUseCase {
        return ChangePasswordInteractor(repository)
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
    ): SaveChatBotHistoryUseCase {
        return SaveChatBotHistoryInteractor(repository)
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
    fun provideGetAllPsychologistUseCase(
        repository: IPsychologistRepository
    ): GetAllPsychologistUseCase {
        return GetAllPsychologistInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideGetPsychologistUseCase(
        repository: IPsychologistRepository
    ): GetPsychologistUseCase {
        return GetPsychologistInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideGetTherapyUseCase(
        repository: ITherapyRepository
    ): GetActiveTherapyUseCase {
        return GetActiveActiveTherapyInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideGetTherapySchedulesUseCase(
        repository: ITherapyRepository
    ): GetTherapySchedulesUseCase {
        return GetTherapyScheduleInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideGetChatHistoryUseCase(
        repository: ITherapyRepository
    ): GetChatHistoryUseCase {
        return GetChatHistoryInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideSendChatUseCase(
        repository: ITherapyRepository
    ): SendChatUseCase {
        return SendChatInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideValidatorUseCase(
        validateEmailUseCase: ValidateEmailUseCase,
        validateNameUseCase: ValidateNameUseCase,
        validatePasswordUseCase: ValidatePasswordUseCase,
        validateCurrentPasswordUseCase: ValidateCurrentPasswordUseCase,
        validatePasswordConfirmationUseCase: ValidatePasswordConfirmationUseCase,
        validateVerificationCodeUseCase: ValidateVerificationCodeUseCase,
        validateAgeUseCase: ValidateAgeUseCase
    ): ValidatorUseCase {
        return ValidatorInteractor(
            validateEmailUseCase,
            validateNameUseCase,
            validatePasswordUseCase,
            validateCurrentPasswordUseCase,
            validatePasswordConfirmationUseCase,
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
    fun provideValidateCurrentPasswordUseCase(): ValidateCurrentPasswordUseCase {
        return ValidateCurrentPasswordInteractor()
    }

    @Provides
    @Singleton
    fun provideValidateConfirmPasswordUseCase(): ValidatePasswordConfirmationUseCase {
        return ValidatePasswordConfirmationInteractor()
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