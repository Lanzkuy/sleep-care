package com.lans.sleep_care.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(private val context: Context) {
    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
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

    suspend fun clear() {
        context.dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN)
        }
    }
}