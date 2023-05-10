package com.kira.android_base.base.database.daos

import com.kira.android_base.base.database.entities.User

class FakeUserDao : UserDao() {

    companion object {
        const val ERROR_DELETE_ALL_USER_FAILED = "Delete all users failed"
    }

    private var shouldReturnDeleteAllError = false
    private val databaseUsers = mutableListOf<User>()

    override fun getUser(): User? {
        return databaseUsers.firstOrNull()
    }

    override fun getAllUser(): List<User> {
        return databaseUsers.toList()
    }

    override fun deleteAllUser(): Int {
        if (shouldReturnDeleteAllError) {
            throw Exception(ERROR_DELETE_ALL_USER_FAILED)
        }
        val size = databaseUsers.size
        databaseUsers.clear()
        return size
    }

    override fun insert(vararg t: User): List<Long> {
        return t.map {
            val id = it.id ?: databaseUsers.size
            databaseUsers.add(it.copy(id = id))
            return@map id.toLong()
        }
    }

    override fun update(vararg t: User): Int {
        t.forEach { updatingUser ->
            databaseUsers.replaceAll { if (it.id == updatingUser.id) updatingUser else it }
        }
        return t.size
    }

    override fun delete(vararg t: User): Int {
        t.forEach { deletingUser ->
            databaseUsers.removeIf { it.id == deletingUser.id }
        }
        return t.size
    }

    fun setShouldReturnDeleteAllError(value: Boolean) {
        shouldReturnDeleteAllError = value
    }
}
