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
import com.lans.sleep_care.domain.interactor.logbook.CreateLogbookAnswerInteractor
import com.lans.sleep_care.domain.interactor.logbook.GetAreasInteractor
import com.lans.sleep_care.domain.interactor.logbook.GetEmotionsInteractor
import com.lans.sleep_care.domain.interactor.logbook.GetLogbookAnswersInteractor
import com.lans.sleep_care.domain.interactor.logbook.GetLogbookQuestionsInteractor
import com.lans.sleep_care.domain.interactor.logbook.GetProblemsInteractor
import com.lans.sleep_care.domain.interactor.logbook.GetSleepDiariesInteractor
import com.lans.sleep_care.domain.interactor.logbook.GetSleepDiaryDetailInteractor
import com.lans.sleep_care.domain.interactor.logbook.UpdateLogbookAnswerInteractor
import com.lans.sleep_care.domain.interactor.payment.CancelPaymentInteractor
import com.lans.sleep_care.domain.interactor.payment.CheckPaymentInteractor
import com.lans.sleep_care.domain.interactor.payment.CreatePaymentInteractor
import com.lans.sleep_care.domain.interactor.payment.UpdatePaymentInteractor
import com.lans.sleep_care.domain.interactor.psychologist.GetAllPsychologistInteractor
import com.lans.sleep_care.domain.interactor.psychologist.GetPsychologistInteractor
import com.lans.sleep_care.domain.interactor.therapy.CreateOrderTherapyInteractor
import com.lans.sleep_care.domain.interactor.therapy.GetActiveTherapyInteractor
import com.lans.sleep_care.domain.interactor.therapy.GetChatHistoryInteractor
import com.lans.sleep_care.domain.interactor.therapy.GetCompletedTherapyInteractor
import com.lans.sleep_care.domain.interactor.therapy.GetOrderTherapyStatusInteractor
import com.lans.sleep_care.domain.interactor.therapy.GetTherapyScheduleInteractor
import com.lans.sleep_care.domain.interactor.therapy.SendChatInteractor
import com.lans.sleep_care.domain.interactor.therapy.UpdateChatReadStatusInteractor
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
import com.lans.sleep_care.domain.repository.ILogbookRepository
import com.lans.sleep_care.domain.repository.IPaymentRepository
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
import com.lans.sleep_care.domain.usecase.logbook.CreateLogbookAnswerUseCase
import com.lans.sleep_care.domain.usecase.logbook.GetAreasUseCase
import com.lans.sleep_care.domain.usecase.logbook.GetEmotionsUseCase
import com.lans.sleep_care.domain.usecase.logbook.GetLogbookAnswersUseCase
import com.lans.sleep_care.domain.usecase.logbook.GetLogbookQuestionsUseCase
import com.lans.sleep_care.domain.usecase.logbook.GetProblemsUseCase
import com.lans.sleep_care.domain.usecase.logbook.GetSleepDiariesUseCase
import com.lans.sleep_care.domain.usecase.logbook.GetSleepDiaryDetailUseCase
import com.lans.sleep_care.domain.usecase.logbook.UpdateLogbookAnswerUseCase
import com.lans.sleep_care.domain.usecase.payment.CancelPaymentUseCase
import com.lans.sleep_care.domain.usecase.payment.CheckPaymentUseCase
import com.lans.sleep_care.domain.usecase.payment.CreatePaymentUseCase
import com.lans.sleep_care.domain.usecase.payment.UpdatePaymentUseCase
import com.lans.sleep_care.domain.usecase.psychologist.GetAllPsychologistUseCase
import com.lans.sleep_care.domain.usecase.psychologist.GetPsychologistUseCase
import com.lans.sleep_care.domain.usecase.therapy.CreateOrderTherapyUseCase
import com.lans.sleep_care.domain.usecase.therapy.GetActiveTherapyUseCase
import com.lans.sleep_care.domain.usecase.therapy.GetChatHistoryUseCase
import com.lans.sleep_care.domain.usecase.therapy.GetCompletedTherapyUseCase
import com.lans.sleep_care.domain.usecase.therapy.GetOrderTherapyStatusUseCase
import com.lans.sleep_care.domain.usecase.therapy.GetTherapySchedulesUseCase
import com.lans.sleep_care.domain.usecase.therapy.SendChatUseCase
import com.lans.sleep_care.domain.usecase.therapy.UpdateChatReadStatusUseCase
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
    fun provideGetActiveTherapyUseCase(
        repository: ITherapyRepository
    ): GetActiveTherapyUseCase {
        return GetActiveTherapyInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideGetCompletedTherapyUseCase(
        repository: ITherapyRepository
    ): GetCompletedTherapyUseCase {
        return GetCompletedTherapyInteractor(repository)
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
    fun provideGetOrderTherapyStatusUseCase(
        repository: ITherapyRepository
    ): GetOrderTherapyStatusUseCase {
        return GetOrderTherapyStatusInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideCreateOrderTherapyUseCase(
        repository: ITherapyRepository
    ): CreateOrderTherapyUseCase {
        return CreateOrderTherapyInteractor(repository)
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
    fun provideUpdateChatReadStatusUseCase(
        repository: ITherapyRepository
    ): UpdateChatReadStatusUseCase {
        return UpdateChatReadStatusInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideGetSleepDiariesUseCase(
        repository: ILogbookRepository
    ): GetSleepDiariesUseCase {
        return GetSleepDiariesInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideGetSleepDiaryDetailUseCase(
        repository: ILogbookRepository
    ): GetSleepDiaryDetailUseCase {
        return GetSleepDiaryDetailInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideGetLogbookQuestionsUseCase(
        repository: ILogbookRepository
    ): GetLogbookQuestionsUseCase {
        return GetLogbookQuestionsInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideGetLogbookAnswersUseCase(
        repository: ILogbookRepository
    ): GetLogbookAnswersUseCase {
        return GetLogbookAnswersInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideCreateLogbookAnswerUseCase(
        repository: ILogbookRepository
    ): CreateLogbookAnswerUseCase {
        return CreateLogbookAnswerInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateLogbookAnswerUseCase(
        repository: ILogbookRepository
    ): UpdateLogbookAnswerUseCase {
        return UpdateLogbookAnswerInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideGetAreasUseCase(
        repository: ILogbookRepository
    ): GetAreasUseCase {
        return GetAreasInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideGetEmotionsUseCase(
        repository: ILogbookRepository
    ): GetEmotionsUseCase {
        return GetEmotionsInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideGetProblemsUseCase(
        repository: ILogbookRepository
    ): GetProblemsUseCase {
        return GetProblemsInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideCheckPaymentUseCase(
        repository: IPaymentRepository
    ): CheckPaymentUseCase {
        return CheckPaymentInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideCreatePaymentUseCase(
        repository: IPaymentRepository
    ): CreatePaymentUseCase {
        return CreatePaymentInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideUpdatePaymentChargeUseCase(
        repository: IPaymentRepository
    ): UpdatePaymentUseCase {
        return UpdatePaymentInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideCancelPaymentChargeUseCase(
        repository: IPaymentRepository
    ): CancelPaymentUseCase {
        return CancelPaymentInteractor(repository)
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