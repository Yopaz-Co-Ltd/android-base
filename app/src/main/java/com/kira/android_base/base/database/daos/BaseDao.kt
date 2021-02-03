package com.kira.android_base.base.database.daos

import androidx.room.*

@Dao
abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg t: T): List<Long>

    @Update
    abstract fun update(vararg t: T): Int

    @Delete
    abstract fun delete(vararg t: T): Int
}
