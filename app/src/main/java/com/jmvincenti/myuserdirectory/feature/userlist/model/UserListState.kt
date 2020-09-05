package com.jmvincenti.myuserdirectory.feature.userlist.model

import com.jmvincenti.myuserdirectory.model.User
import com.jmvincenti.statemachine.SimpleLoadingState
import com.jmvincenti.statemachine.State

data class UserListState(
    val currentPage: Int,
    val loadInitialState: SimpleLoadingState,
    val loadMoreState: SimpleLoadingState,
    val userList: List<User>
): State
