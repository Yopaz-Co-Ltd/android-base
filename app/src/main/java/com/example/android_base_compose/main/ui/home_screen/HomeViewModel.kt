package com.example.android_base_compose.main.ui.home_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_base_compose.base.api.entities.UserResponse
import com.example.android_base_compose.base.datahandling.handleFlowResponse
import com.example.android_base_compose.base.repository.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ViewModel() {

    private val _userList = MutableStateFlow<List<UserResponse>>(listOf())
    val userList = _userList.asStateFlow()

    init {
        viewModelScope.launch {
            remoteDataSource.fetchUsers().handleFlowResponse (
                onError = {
                    Log.e("TAG", "$it: ")
                }
            ) {
                Log.e("TAG", "$it: ", )
                _userList.value = it ?: listOf()
            }
        }
    }
}
