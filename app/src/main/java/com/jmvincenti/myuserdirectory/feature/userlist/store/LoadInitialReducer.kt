package com.jmvincenti.myuserdirectory.feature.userlist.store

import com.jmvincenti.myuserdirectory.feature.userlist.model.LoadInitialAction
import com.jmvincenti.myuserdirectory.feature.userlist.model.UserListAction
import com.jmvincenti.myuserdirectory.feature.userlist.model.UserListState
import com.jmvincenti.statemachine.Reducer
import com.jmvincenti.statemachine.SimpleLoadingState

val initialReducer: Reducer<UserListState, UserListAction> = { state, action ->
    when (action) {
        is LoadInitialAction -> when (action) {
            is LoadInitialAction.Started -> state.copy(
                loadInitialState = SimpleLoadingState.Loading
            )

            is LoadInitialAction.Succeed -> state.copy(
                loadInitialState = SimpleLoadingState.Idle,
                currentPage = 1,
                userList = action.result
            )

            is LoadInitialAction.Failed -> state.copy(
                loadInitialState = SimpleLoadingState.Error(action.error),
                userList = action.cachedList.orEmpty()
            )
        }

        else -> state
    }
}
