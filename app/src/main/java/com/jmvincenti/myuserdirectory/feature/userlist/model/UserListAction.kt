package com.jmvincenti.myuserdirectory.feature.userlist.model

import com.jmvincenti.myuserdirectory.model.User
import com.jmvincenti.statemachine.Action
import com.jmvincenti.statemachine.ErrorMessage

interface UserListAction : Action

// UserList UI commands
sealed class UserListCommand : UserListAction {
    object OnStart : UserListCommand()
    object RequestLoadMore : UserListCommand()
    object RequestRetry : UserListCommand()
    object RequestRetryLoadMore : UserListCommand()
}

// UserList StateMachine actions

sealed class LoadInitialAction : UserListAction {
    object Started : LoadInitialAction()

    data class Succeed(
        val result: List<User>
    ) : LoadInitialAction()

    data class Failed(
        val error: ErrorMessage,
        val cachedList: List<User>?
    ) : LoadInitialAction()
}

sealed class LoadMoreAction : UserListAction {
    object Started : LoadMoreAction()

    data class Succeed(
        val page: Int,
        val result: List<User>
    ) : LoadMoreAction()

    data class Failed(
        val error: ErrorMessage
    ) : LoadMoreAction()
}
