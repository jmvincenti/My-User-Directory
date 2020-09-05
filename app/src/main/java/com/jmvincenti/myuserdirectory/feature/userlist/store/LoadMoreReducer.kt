package com.jmvincenti.myuserdirectory.feature.userlist.store

import com.jmvincenti.myuserdirectory.feature.userlist.model.LoadMoreAction
import com.jmvincenti.myuserdirectory.feature.userlist.model.UserListAction
import com.jmvincenti.myuserdirectory.feature.userlist.model.UserListState
import com.jmvincenti.statemachine.Reducer
import com.jmvincenti.statemachine.SimpleLoadingState

val moreReducer: Reducer<UserListState, UserListAction> = { state, action ->
    when (action) {
        is LoadMoreAction -> when (action) {
            is LoadMoreAction.Started -> state.copy(
                loadMoreState = SimpleLoadingState.Loading
            )

            is LoadMoreAction.Succeed -> state.copy(
                loadMoreState = SimpleLoadingState.Idle,
                currentPage = action.page,
                userList = state.userList + action.result
            )

            is LoadMoreAction.Failed -> state.copy(
                loadMoreState = SimpleLoadingState.Error(action.error)
            )
        }

        else -> state
    }
}
