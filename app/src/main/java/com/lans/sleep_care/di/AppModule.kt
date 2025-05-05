package com.lans.sleep_care.di

import android.content.Context
import com.lans.sleep_care.common.Constant.BASE_URL
import com.lans.sleep_care.data.repository.AuthRepository
import com.lans.sleep_care.data.source.local.DataStoreManager
import com.lans.sleep_care.data.source.network.api.SleepCareApi
import com.lans.sleep_care.data.source.network.interceptor.AuthInterceptor
import com.lans.sleep_care.data.source.network.interceptor.HeaderInterceptor
import com.lans.sleep_care.domain.interactor.IsAuthenticatedInteractor
import com.lans.sleep_care.domain.interactor.LoginInteractor
import com.lans.sleep_care.domain.interactor.LogoutInteractor
import com.lans.sleep_care.domain.interactor.RegisterInteractor
import com.lans.sleep_care.domain.interactor.StoreSessionInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidateAgeInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidateConfirmPasswordInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidateEmailInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidateNameInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidatePasswordInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidateVerificationCodeInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidatorInteractor
import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.usecase.IsAuthenticatedUseCase
import com.lans.sleep_care.domain.usecase.LoginUseCase
import com.lans.sleep_care.domain.usecase.LogoutUseCase
import com.lans.sleep_care.domain.usecase.RegisterUseCase
import com.lans.sleep_care.domain.usecase.StoreSessionUseCase
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
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofitClient(
        dataStoreManager: DataStoreManager
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(AuthInterceptor(dataStoreManager))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideSleepCareApi(client: OkHttpClient): SleepCareApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: SleepCareApi,
        dateStoreManager: DataStoreManager
    ): IAuthRepository {
        return AuthRepository(
            api,
            dateStoreManager
        )
    }

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
    fun provideLogoutUseCase(
        repository: IAuthRepository
    ): LogoutUseCase {
        return LogoutInteractor(repository)
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