package com.jmvincenti.myuserdirectory.feature.userprofile.model

import com.jmvincenti.myuserdirectory.model.User

sealed class UserProfileResult {
    data class Success(val user: User) : UserProfileResult()
    object Error : UserProfileResult()
}
