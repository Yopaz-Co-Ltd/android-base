package com.kira.android_base.base.api.models.response

import com.kira.android_base.base.database.entities.User
import com.squareup.moshi.Json

data class LoginResponse(
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "name")
    val name: String?
) {
    fun toLocalUser() = User(id, name ?: "")
}
