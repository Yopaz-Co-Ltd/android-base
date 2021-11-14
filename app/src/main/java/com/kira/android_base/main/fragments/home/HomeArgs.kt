package com.kira.android_base.main.fragments.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeArgs(
    val userName: String?,
    val age: Int?
) : Parcelable
