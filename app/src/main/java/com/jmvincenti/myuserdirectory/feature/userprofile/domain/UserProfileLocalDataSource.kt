package com.jmvincenti.myuserdirectory.feature.userprofile.domain

import com.jmvincenti.myuserdirectory.model.User

interface UserProfileLocalDataSource {
    fun getUser(userId : String) : User?
}
