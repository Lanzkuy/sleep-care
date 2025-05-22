package com.lans.sleep_care.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(private val context: Context) {
    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        val PAYMENT_TOKEN = stringPreferencesKey("PAYMENT_TOKEN")
        val ORDER_ID = stringPreferencesKey("ORDER_ID")
        val PSYCHOLOGIST_ID = intPreferencesKey("PSYCHOLOGIST_ID")
        private const val DATASTORE = "sleepcareapp"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE)

    suspend fun <T> storeData(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    fun getAccessToken(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN] ?: ""
        }
    }

    fun getPaymentToken(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PAYMENT_TOKEN] ?: ""
        }
    }

    fun getOrderId(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[ORDER_ID] ?: ""
        }
    }

    fun getPsychologistId(): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[PSYCHOLOGIST_ID] ?: 0
        }
    }

    suspend fun deleteAccessToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN)
        }
    }

    suspend fun deletePaymentToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(PAYMENT_TOKEN)
        }
    }

    suspend fun deleteOrderId() {
        context.dataStore.edit { preferences ->
            preferences.remove(ORDER_ID)
        }
    }

    suspend fun deletePsychologistId() {
        context.dataStore.edit { preferences ->
            preferences.remove(PSYCHOLOGIST_ID)
        }
    }
}