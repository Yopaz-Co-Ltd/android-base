package com.kira.android_base.base.datahandling

data class Result<T>(
    val data: T?,
    val error: Error?
)
