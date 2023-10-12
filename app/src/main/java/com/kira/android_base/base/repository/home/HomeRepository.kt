package com.kira.android_base.base.repository.home

import com.kira.android_base.base.Result
import com.kira.android_base.base.api.home.models.home.HomeResponseModel
import com.kira.android_base.main.fragments.home.PostItemHomeModel

interface HomeRepository {
    suspend fun getListPostHome(): Result<List<PostItemHomeModel>>
}
