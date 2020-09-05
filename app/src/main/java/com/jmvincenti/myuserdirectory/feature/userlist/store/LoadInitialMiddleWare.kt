package com.jmvincenti.myuserdirectory.feature.userlist.store

import com.jmvincenti.myuserdirectory.feature.userlist.domain.UserListUseCase
import com.jmvincenti.myuserdirectory.feature.userlist.model.*
import com.jmvincenti.statemachine.Middleware
import com.jmvincenti.statemachine.Next
import com.jmvincenti.statemachine.SimpleLoadingState
import com.jmvincenti.statemachine.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoadInitialMiddleWare(
    private val scope: CoroutineScope,
    private val useCase: UserListUseCase
) : Middleware<UserListState, UserListAction> {

    var job: Job? = null

    override fun invoke(
        action: UserListAction,
        store: Store<UserListState, UserListAction>,
        next: Next<UserListAction>
    ) {
        next(action)

        val currentState = store.currentState
        val page = currentState.currentPage
        val initialState = currentState.loadInitialState

        when {
            action is UserListCommand.OnStart &&
                    page == 0 &&
                    initialState is SimpleLoadingState.Idle -> {

                next(LoadInitialAction.Started)
                startLoadInitial(store)
            }

            action is UserListCommand.RequestRetry &&
                    initialState is SimpleLoadingState.Error -> {

                next(LoadInitialAction.Started)
                startLoadInitial(store)
            }
        }
    }

    private fun startLoadInitial(store: Store<UserListState, UserListAction>) {
        job?.cancel()
        job = scope.launch {
            when (val result = useCase.loadInitial()) {
                is InitialUserListResult.Success -> store.dispatch(
                    LoadInitialAction.Succeed(result.userList)
                )

                is InitialUserListResult.Error -> store.dispatch(
                    LoadInitialAction.Failed(
                        error = result.errorMessage,
                        cachedList = result.cachedList
                    )
                )
            }
        }
    }
}
