package com.example.android_base_compose.base.repository

import com.example.android_base_compose.base.database.AppDatabase
import com.example.android_base_compose.base.preferences_datastore.PreferencesDataStore
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    val appDatabase: AppDatabase,
    val preferencesDataStore: PreferencesDataStore
)
