package com.kira.android_base.main.fragments.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kira.android_base.base.api.home.models.home.HomeResponseModel
import com.kira.android_base.base.repository.auth.AuthRepository
import com.kira.android_base.base.repository.home.DefaultHomeRepository
import com.kira.android_base.base.repository.home.HomeRepository
import com.kira.android_base.base.ui.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.core.component.get

class HomeViewModel(
    private val homeRepository: HomeRepository
) : BaseViewModel() {

    private var _listPostLiveData = MutableLiveData<List<PostItemHomeModel>?>()
    val mutableListPost: LiveData<List<PostItemHomeModel>?> = _listPostLiveData

    fun getListPostHome(shouldShowLoading: Boolean = true) {
        viewModelScope.launch {
            val data = homeRepository.getListPostHome()
            if(shouldShowLoading){
                _loadingLiveData.postValue(true)
            }
            _listPostLiveData.value = data.data
            _loadingLiveData.postValue(false)
        }
    }
}
