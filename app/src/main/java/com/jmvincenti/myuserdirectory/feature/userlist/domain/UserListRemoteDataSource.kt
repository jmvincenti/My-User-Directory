package com.jmvincenti.myuserdirectory.feature.userlist.domain

import com.jmvincenti.myuserdirectory.model.User
import com.jmvincenti.statemachine.ErrorMessage

interface UserListRemoteDataSource {
    suspend fun loadInitial(pageSize: Int): UserListRemoteResult
    suspend fun loadMore(page: Int, pageSize: Int): UserListRemoteResult
}

sealed class UserListRemoteResult {
    data class Success(val userList: List<User>) : UserListRemoteResult()
    data class Error(val error: ErrorMessage) : UserListRemoteResult()
}
