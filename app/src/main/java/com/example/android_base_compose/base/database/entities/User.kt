package com.example.android_base_compose.base.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val uId: Int,
    val displayName: String? = null
)
