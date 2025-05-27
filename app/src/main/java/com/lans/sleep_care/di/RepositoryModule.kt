package com.lans.sleep_care.di

import com.lans.sleep_care.data.repository.AuthRepository
import com.lans.sleep_care.data.repository.ChatBotRepository
import com.lans.sleep_care.data.repository.LogbookRepository
import com.lans.sleep_care.data.repository.PaymentRepository
import com.lans.sleep_care.data.repository.PsychologistRepository
import com.lans.sleep_care.data.repository.TherapyRepository
import com.lans.sleep_care.data.repository.UserRepository
import com.lans.sleep_care.data.source.local.DataStoreManager
import com.lans.sleep_care.data.source.local.dao.ChatBotHistoryDao
import com.lans.sleep_care.data.source.network.api.ChatBotApi
import com.lans.sleep_care.data.source.network.api.SleepCareApi
import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.repository.IChatBotRepository
import com.lans.sleep_care.domain.repository.ILogbookRepository
import com.lans.sleep_care.domain.repository.IPaymentRepository
import com.lans.sleep_care.domain.repository.IPsychologistRepository
import com.lans.sleep_care.domain.repository.ITherapyRepository
import com.lans.sleep_care.domain.repository.IUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(
        api: SleepCareApi,
        dataStoreManager: DataStoreManager
    ): IAuthRepository {
        return AuthRepository(
            api,
            dataStoreManager
        )
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        api: SleepCareApi
    ): IUserRepository {
        return UserRepository(api)
    }

    @Provides
    @Singleton
    fun provideChatBotRepository(
        api: ChatBotApi,
        dao: ChatBotHistoryDao
    ): IChatBotRepository {
        return ChatBotRepository(api, dao)
    }

    @Provides
    @Singleton
    fun providePsychologistRepository(
        api: SleepCareApi
    ): IPsychologistRepository {
        return PsychologistRepository(api)
    }

    @Provides
    @Singleton
    fun provideTherapyRepository(
        api: SleepCareApi
    ): ITherapyRepository {
        return TherapyRepository(api)
    }

    @Provides
    @Singleton
    fun provideLogbookRepository(
        api: SleepCareApi
    ): ILogbookRepository {
        return LogbookRepository(api)
    }

    @Provides
    @Singleton
    fun providePaymentRepository(
        api: SleepCareApi
    ): IPaymentRepository {
        return PaymentRepository(api)
    }
}