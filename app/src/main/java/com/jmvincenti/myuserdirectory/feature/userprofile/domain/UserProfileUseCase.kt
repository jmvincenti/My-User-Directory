package com.jmvincenti.myuserdirectory.feature.userprofile.domain

import com.jmvincenti.myuserdirectory.feature.userprofile.model.UserProfileResult

class UserProfileUseCase(
    private val localDataSource: UserProfileLocalDataSource
) {

    suspend fun getUserList(userId: String): UserProfileResult =
        when (val user = localDataSource.getUser(userId)) {
            null -> UserProfileResult.Error
            else -> UserProfileResult.Success(user)
        }
}
