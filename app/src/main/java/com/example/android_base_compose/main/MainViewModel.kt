package com.example.android_base_compose.main

import androidx.lifecycle.ViewModel
import com.example.android_base_compose.base.repository.BaseRepository
import com.example.android_base_compose.base.until.IS_LOGIN_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val baseRepository: BaseRepository
) : ViewModel() {
    val isLoggedState = baseRepository.localDataSource.preferencesDataStore.getData<Boolean>(IS_LOGIN_KEY)
}
