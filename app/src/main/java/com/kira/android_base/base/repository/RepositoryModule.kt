package com.kira.android_base.base.repository

import com.kira.android_base.base.repository.auth.AuthRepository
import com.kira.android_base.base.repository.auth.DefaultAuthRepository
import com.kira.android_base.base.repository.user.DefaultUserRepository
import com.kira.android_base.base.repository.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindAuthRepository(defaultAuthRepository: DefaultAuthRepository): AuthRepository

    @Binds
    fun bindUserRepository(defaultUserRepository: DefaultUserRepository): UserRepository
}
