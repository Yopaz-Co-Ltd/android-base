package com.kira.android_base.base.repository.home

import android.content.Context
import android.util.Log
import com.kira.android_base.base.api.callApi
import com.kira.android_base.base.api.home.HomeApi
import com.kira.android_base.base.Result
import com.kira.android_base.base.toResult
import com.kira.android_base.main.fragments.home.PostItemHomeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultHomeRepository(private val context: Context, private val homeApi: HomeApi): HomeRepository {

    override suspend fun getListPostHome(): Result<List<PostItemHomeModel>> =
        withContext(Dispatchers.IO) {
            val result = callApi { homeApi.getListPostHome() }
            val data = result?.data
            data?.let {
                return@withContext it.toResult()
            } ?: run {
                Log.e("ERROR", "${result?.error}")
                return@withContext result?.error!!.toResult<List<PostItemHomeModel>>()
            }
        }

}
