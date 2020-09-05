package com.jmvincenti.myuserdirectory.feature.userlist.model

import com.jmvincenti.myuserdirectory.model.User
import com.jmvincenti.statemachine.ErrorMessage

sealed class InitialUserListResult {
    data class Success(val userList: List<User>) : InitialUserListResult()
    data class Error(
        val errorMessage: ErrorMessage,
        val cachedList: List<User>?
    ) : InitialUserListResult()
}

sealed class MoreUserListResult {
    data class Success(val userList: List<User>) : MoreUserListResult()
    data class Error(val errorMessage: ErrorMessage) : MoreUserListResult()
}
