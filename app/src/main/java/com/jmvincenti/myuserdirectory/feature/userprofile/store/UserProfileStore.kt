package com.jmvincenti.myuserdirectory.feature.userprofile.store

import com.jmvincenti.myuserdirectory.feature.userprofile.domain.UserProfileUseCase
import com.jmvincenti.myuserdirectory.feature.userprofile.model.LoadUserState
import com.jmvincenti.myuserdirectory.feature.userprofile.model.UserProfileAction
import com.jmvincenti.myuserdirectory.feature.userprofile.model.UserProfileState
import com.jmvincenti.statemachine.BaseStoreImpl
import com.jmvincenti.statemachine.Middleware
import com.jmvincenti.statemachine.Reducer
import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.Executor

class UserProfileStore(
    scope: CoroutineScope,
    useCase: UserProfileUseCase,
    override val executor: Executor,
    override val reducers: List<Reducer<UserProfileState, UserProfileAction>> =
        listOf(
            loadReducer
        ),
    override val middlewares: List<Middleware<UserProfileState, UserProfileAction>> =
        listOf(
            LoadUserMiddleWare(scope, useCase)
        )
) : BaseStoreImpl<UserProfileState, UserProfileAction>(
    initialState = UserProfileState(
        user = null,
        loadState = LoadUserState.Idle
    )
)
