package com.kira.android_base.base.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) @field:Json(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "name") @field:Json(name = "name")
    val name: String
)
