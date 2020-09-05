package com.jmvincenti.myuserdirectory.feature.userlist.store

import com.jmvincenti.myuserdirectory.feature.userlist.domain.UserListUseCase
import com.jmvincenti.myuserdirectory.feature.userlist.model.UserListAction
import com.jmvincenti.myuserdirectory.feature.userlist.model.UserListState
import com.jmvincenti.statemachine.BaseStoreImpl
import com.jmvincenti.statemachine.Middleware
import com.jmvincenti.statemachine.Reducer
import com.jmvincenti.statemachine.SimpleLoadingState
import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.Executor

class UserListStore(
    scope: CoroutineScope,
    useCase: UserListUseCase,
    override val executor: Executor,
    override val reducers: List<Reducer<UserListState, UserListAction>> =
        listOf(
            initialReducer,
            moreReducer
        ),
    override val middlewares: List<Middleware<UserListState, UserListAction>> =
        listOf(
            LoadInitialMiddleWare(scope, useCase),
            LoadMoreMiddleWare(scope, useCase)
        )
) : BaseStoreImpl<UserListState, UserListAction>(
    initialState = UserListState(
        currentPage = 0,
        loadMoreState = SimpleLoadingState.Idle,
        loadInitialState = SimpleLoadingState.Idle,
        userList = emptyList()
    )
)
