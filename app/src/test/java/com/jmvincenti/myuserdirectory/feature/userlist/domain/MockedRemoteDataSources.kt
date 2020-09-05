package com.jmvincenti.myuserdirectory.feature.userlist.domain

import com.jmvincenti.myuserdirectory.model.User
import com.jmvincenti.statemachine.ErrorMessage

class SucceedRemoteDataSource(
    private val result: List<User>
) : UserListRemoteDataSource {
    override suspend fun loadInitial(pageSize: Int): UserListRemoteResult =
        UserListRemoteResult.Success(result)

    override suspend fun loadMore(page: Int, pageSize: Int): UserListRemoteResult =
        UserListRemoteResult.Success(result)
}

class FailingRemoteDataSource(
    private val error: ErrorMessage
) : UserListRemoteDataSource {
    override suspend fun loadInitial(pageSize: Int): UserListRemoteResult =
        UserListRemoteResult.Error(error)

    override suspend fun loadMore(page: Int, pageSize: Int): UserListRemoteResult =
        UserListRemoteResult.Error(error)
}
