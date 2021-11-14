package com.kira.android_base.base.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String?,
    val age: Int?
)
