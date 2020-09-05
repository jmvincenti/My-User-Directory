package com.jmvincenti.myuserdirectory.feature.userlist.di

import com.jmvincenti.myuserdirectory.feature.userlist.data.UserListLocalDataSourceImpl
import com.jmvincenti.myuserdirectory.feature.userlist.data.UserListRemoteDataSourceImpl
import com.jmvincenti.myuserdirectory.feature.userlist.domain.UserListLocalDataSource
import com.jmvincenti.myuserdirectory.feature.userlist.domain.UserListRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class UserListModule {

    @Binds
    abstract fun bindUserListLocalDataSource(
        userListLocalDataSourceImpl: UserListLocalDataSourceImpl
    ): UserListLocalDataSource

    @Binds
    abstract fun bindUserListRemoteDataSource(
        userListRemoteDataSourceImpl: UserListRemoteDataSourceImpl
    ): UserListRemoteDataSource
}
