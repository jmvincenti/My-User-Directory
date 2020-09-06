package com.jmvincenti.myuserdirectory.feature.userprofile.store

import com.jmvincenti.myuserdirectory.feature.userprofile.model.LoadAction
import com.jmvincenti.myuserdirectory.feature.userprofile.model.LoadUserState
import com.jmvincenti.myuserdirectory.feature.userprofile.model.UserProfileAction
import com.jmvincenti.myuserdirectory.feature.userprofile.model.UserProfileState
import com.jmvincenti.statemachine.Reducer

val loadReducer: Reducer<UserProfileState, UserProfileAction> = { state, action ->
    when (action) {
        is LoadAction -> when (action) {
            is LoadAction.Started -> state.copy(
                loadState = LoadUserState.Loading
            )

            is LoadAction.Succeed -> state.copy(
                loadState = LoadUserState.Idle,
                user = action.result
            )

            is LoadAction.Failed -> state.copy(
                loadState = LoadUserState.Error
            )
        }

        else -> state
    }
}
