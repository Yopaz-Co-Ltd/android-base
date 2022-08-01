package com.example.android_base_compose.main.ui.account_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_base_compose.base.repository.LocalDataSource
import com.example.android_base_compose.base.until.KEY_IS_LOGIN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val localDataSource: LocalDataSource
) : ViewModel() {
    fun logOut() {
        viewModelScope.launch {
            localDataSource.preferencesDataStore.saveData(KEY_IS_LOGIN, false)
        }
    }
}
