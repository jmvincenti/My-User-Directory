package com.jmvincenti.myuserdirectory.feature.userprofile.store

import com.jmvincenti.myuserdirectory.feature.userprofile.domain.UserProfileUseCase
import com.jmvincenti.myuserdirectory.feature.userprofile.model.*
import com.jmvincenti.statemachine.Middleware
import com.jmvincenti.statemachine.Next
import com.jmvincenti.statemachine.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoadUserMiddleWare(
    private val scope: CoroutineScope,
    private val useCase: UserProfileUseCase
) : Middleware<UserProfileState, UserProfileAction> {

    var job: Job? = null

    override fun invoke(
        action: UserProfileAction,
        store: Store<UserProfileState, UserProfileAction>,
        next: Next<UserProfileAction>
    ) {
        next(action)

        when (action) {
            is UserProfileCommand.Init -> {
                next(LoadAction.Started)
                job?.cancel()
                job = scope.launch {
                    when (val result = useCase.getUserList(action.userId)) {
                        is UserProfileResult.Success -> store.dispatch(LoadAction.Succeed(result.user))
                        UserProfileResult.Error -> store.dispatch(LoadAction.Failed)
                    }
                }
            }
        }
    }
}
