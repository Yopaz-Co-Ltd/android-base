package com.kira.android_base.main.fragments.login

import androidx.lifecycle.MutableLiveData
import com.kira.android_base.base.api.models.response.LoginResponse
import com.kira.android_base.base.database.entities.User
import com.kira.android_base.base.datahandling.Error
import com.kira.android_base.base.datahandling.Result
import com.kira.android_base.base.datahandling.ResultObserver
import com.kira.android_base.base.ui.BaseViewModel
import io.reactivex.rxkotlin.plusAssign

class LoginViewModel(
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    val loginLiveData = MutableLiveData<LoginResponse>()
    val insertLocalUserLiveData = MutableLiveData<List<Long>>()
    val errorLiveData = MutableLiveData<Error>()

    fun login(
        loginCallback: ((Result<LoginResponse>) -> Unit)? = null,
        insertLocalUserCallback: ((Result<List<Long>>) -> Unit)? = null
    ) {
        compositeDisposable += loginRepository.login()
            .subscribeWith(LoginResultObserver(loginCallback, insertLocalUserCallback))
    }

    private inner class LoginResultObserver(
        private val loginCallback: ((Result<LoginResponse>) -> Unit)? = null,
        private val insertLocalUserCallback: ((Result<List<Long>>) -> Unit)? = null
    ) : ResultObserver<LoginResponse>() {
        override fun onSuccess(success: LoginResponse) {
            loginLiveData.postValue(success)
            insertLocalUser(success.toLocalUser(), insertLocalUserCallback)
        }

        override fun onError(error: Error) {
            super.onError(error)
            errorLiveData.postValue(error)
        }

        override fun onNext(result: Result<LoginResponse>) {
            super.onNext(result)
            loginCallback?.invoke(result)
        }
    }

    fun insertLocalUser(
        user: User,
        insertLocalUserCallback: ((Result<List<Long>>) -> Unit)? = null
    ) {
        compositeDisposable += loginRepository.insertLocalUser(user)
            .subscribeWith(InsertLocalUserObserver(insertLocalUserCallback))
    }

    private inner class InsertLocalUserObserver(
        private val insertLocalUserCallback: ((Result<List<Long>>) -> Unit)? = null
    ) : ResultObserver<List<Long>>() {
        override fun onSuccess(success: List<Long>) {
            insertLocalUserLiveData.postValue(success)
        }

        override fun onNext(result: Result<List<Long>>) {
            super.onNext(result)
            insertLocalUserCallback?.invoke(result)
        }
    }
}
