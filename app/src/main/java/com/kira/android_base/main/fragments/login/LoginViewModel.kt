package com.kira.android_base.main.fragments.login

import androidx.lifecycle.MutableLiveData
import com.kira.android_base.base.api.models.response.BaseResponse
import com.kira.android_base.base.database.entities.User
import com.kira.android_base.base.datahandling.Error
import com.kira.android_base.base.datahandling.Result
import com.kira.android_base.base.ui.BaseViewModel

class LoginViewModel(
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    val loginLiveData = MutableLiveData<BaseResponse<User>>()
    val insertLocalUserLiveData = MutableLiveData<List<Long>>()
    val errorLiveData = MutableLiveData<Error>()

    fun login(
        loginCallback: ((Result<BaseResponse<User>>) -> Unit)? = null,
        insertLocalUserCallback: ((Result<List<Long>>) -> Unit)? = null
    ) {
        subscribeCallback(loginRepository.login()) { result ->
            loginCallback?.invoke(result)
            result.data?.let { success ->
                loginLiveData.postValue(success)
                success.data?.let {
                    insertLocalUser(it, insertLocalUserCallback)
                }
                return@subscribeCallback
            }
            result.error?.let { error ->
                errorLiveData.postValue(error)
            }
        }
    }

    private fun insertLocalUser(
        user: User,
        insertLocalUserCallback: ((Result<List<Long>>) -> Unit)? = null
    ) {
        subscribeCallback(loginRepository.insertLocalUser(user)) { result ->
            insertLocalUserCallback?.invoke(result)
            result.data?.let {
                insertLocalUserLiveData.postValue(it)
            }
        }
    }
}
