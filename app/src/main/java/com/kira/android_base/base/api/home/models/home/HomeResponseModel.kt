package com.kira.android_base.base.api.home.models.home

data class HomeResponseModel(
    val id: Int,
    val user_id: Int,
    val title: String,
    val content: String,
    val created_at: String,
    val updated_at: String,
    val deleted_at: String?,
    val user: User,
    val tags: List<Tag>
)

data class User(
    val id: Int,
    val name: String?,
    val avatar_url: String?,
    val bio: String?,
    val deleted_at: String?
)

data class Tag(
    val id: Int,
    val name: String
)
