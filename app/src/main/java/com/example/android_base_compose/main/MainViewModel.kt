package com.example.android_base_compose.main

import androidx.lifecycle.ViewModel
import com.example.android_base_compose.base.repository.BaseRepository
import com.example.android_base_compose.base.until.KEY_IS_LOGIN
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    baseRepository: BaseRepository
) : ViewModel() {
    val isLoggedState =
        baseRepository.localDataSource.preferencesDataStore.getData<Boolean>(KEY_IS_LOGIN)
}
