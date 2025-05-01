package com.lans.sleep_care.di

import com.lans.sleep_care.common.Constant.BASE_URL
import com.lans.sleep_care.data.repository.AuthRepository
import com.lans.sleep_care.data.source.network.api.SleepCareApi
import com.lans.sleep_care.domain.interactor.LoginInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidateEmailInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidateNameInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidatePasswordInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidateVerificationCodeInteractor
import com.lans.sleep_care.domain.interactor.validator.ValidatorInteractor
import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.usecase.LoginUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidateEmailUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidateNameUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidatePasswordUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidateVerificationCodeUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidatorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
//        dataStoreManager: DataStoreManager
    ): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
//            .authenticator(AuthAuthenticator(dataStoreManager))
//            .addInterceptor(AuthInterceptor(dataStoreManager))
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
    fun provideAuthRepository(
        api: SleepCareApi,
//        dateStoreManager: DataStoreManager
    ): IAuthRepository {
        return AuthRepository(
            api,
//            dateStoreManager
        )
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(
        repository: IAuthRepository
    ): LoginUseCase {
        return LoginInteractor(
            repository
        )
    }

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