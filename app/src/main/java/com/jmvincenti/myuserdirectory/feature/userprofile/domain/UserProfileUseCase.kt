package com.jmvincenti.myuserdirectory.feature.userprofile.domain

import com.jmvincenti.myuserdirectory.feature.userprofile.model.UserProfileResult
import javax.inject.Inject

class UserProfileUseCase @Inject constructor(
    private val localDataSource: UserProfileLocalDataSource
) {

    suspend fun getUserList(userId: String): UserProfileResult =
        when (val user = localDataSource.getUser(userId)) {
            null -> UserProfileResult.Error
            else -> UserProfileResult.Success(user)
        }
}
