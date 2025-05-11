package com.lans.sleep_care.di

import com.lans.sleep_care.data.repository.AuthRepository
import com.lans.sleep_care.data.repository.ChatBotRepository
import com.lans.sleep_care.data.repository.PsychologistRepository
import com.lans.sleep_care.data.repository.UserRepository
import com.lans.sleep_care.data.source.local.DataStoreManager
import com.lans.sleep_care.data.source.local.dao.ChatBotHistoryDao
import com.lans.sleep_care.data.source.network.api.ChatBotApi
import com.lans.sleep_care.data.source.network.api.SleepCareApi
import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.repository.IChatBotRepository
import com.lans.sleep_care.domain.repository.IPsychologistRepository
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
        dateStoreManager: DataStoreManager
    ): IAuthRepository {
        return AuthRepository(
            api,
            dateStoreManager
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
}