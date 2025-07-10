package com.lans.sleep_care.di

import android.content.Context
import androidx.room.Room
import com.lans.sleep_care.common.Constant.BASE_CHATBOT_URL
import com.lans.sleep_care.common.Constant.BASE_URL
import com.lans.sleep_care.data.source.local.AppDatabase
import com.lans.sleep_care.data.source.local.DataStoreManager
import com.lans.sleep_care.data.source.local.dao.ChatBotHistoryDao
import com.lans.sleep_care.data.source.network.api.ChatBotApi
import com.lans.sleep_care.data.source.network.api.SleepCareApi
import com.lans.sleep_care.data.source.network.interceptor.AuthInterceptor
import com.lans.sleep_care.data.source.network.interceptor.HeaderInterceptor
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
    fun provideChatBotApi(client: OkHttpClient): ChatBotApi {
        return Retrofit.Builder()
            .baseUrl(BASE_CHATBOT_URL)
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
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "sleepCareDB"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideChatBotHistoryDao(appDatabase: AppDatabase): ChatBotHistoryDao {
        return appDatabase.chatBotHistoryDao
    }
}