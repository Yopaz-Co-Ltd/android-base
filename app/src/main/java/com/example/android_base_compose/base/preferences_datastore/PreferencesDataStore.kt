package com.example.android_base_compose.base.preferences_datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataStore @Inject constructor(val dataStore: DataStore<Preferences>) {

    companion object {
        private val TAG = this::class.simpleName
        private const val SAVE_DATA_ERROR_MESSAGE = "Save data error, value type not supported"
    }

    suspend fun saveData(key: String, value: Any) {
        when (value) {
            is Int -> {
                dataStore.edit { preferences -> preferences[intPreferencesKey(key)] = value }
            }
            is Double -> {
                dataStore.edit { preferences -> preferences[doublePreferencesKey(key)] = value }
            }
            is String -> {
                dataStore.edit { preferences -> preferences[stringPreferencesKey(key)] = value }
            }
            is Boolean -> {
                dataStore.edit { preferences -> preferences[booleanPreferencesKey(key)] = value }
            }
            is Float -> {
                dataStore.edit { preferences -> preferences[floatPreferencesKey(key)] = value }
            }
            is Long -> {
                dataStore.edit { preferences -> preferences[longPreferencesKey(key)] = value }
            }
            else -> {
                Log.e(TAG, SAVE_DATA_ERROR_MESSAGE)
            }
        }
    }

    inline fun <reified T> getData(key: String, defaultValue: T? = null): Flow<T?> =
        dataStore.data.catch { exception ->
            exception.stackTrace
        }.map { preferences ->
            when (T::class) {
                Int::class -> {
                    preferences[intPreferencesKey(key)] as? T
                }
                Double::class -> {
                    preferences[doublePreferencesKey(key)] as? T
                }
                String::class -> {
                    preferences[stringPreferencesKey(key)] as? T
                }
                Boolean::class -> {
                    preferences[booleanPreferencesKey(key)] as? T
                }
                Float::class -> {
                    preferences[floatPreferencesKey(key)] as? T
                }
                Long::class -> {
                    preferences[longPreferencesKey(key)] as? T
                }
                else -> null
            }
        }

    suspend fun clearData() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
