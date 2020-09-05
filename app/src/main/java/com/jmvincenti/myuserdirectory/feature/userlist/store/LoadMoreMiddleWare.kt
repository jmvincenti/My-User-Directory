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

class LoadMoreMiddleWare(
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
        val moreState = currentState.loadMoreState

        when {
            action is UserListCommand.RequestLoadMore &&
                    page > 0 &&
                    moreState is SimpleLoadingState.Idle -> {

                next(LoadMoreAction.Started)
                startLoadMore(page, store)
            }

            action is UserListCommand.RequestRetryLoadMore &&
                    page > 0 &&
                    moreState is SimpleLoadingState.Error -> {

                next(LoadMoreAction.Started)
                startLoadMore(page, store)
            }
        }
    }

    private fun startLoadMore(currentPage: Int, store: Store<UserListState, UserListAction>) {
        job = scope.launch {
            val targetPage = currentPage + 1
            when (val result = useCase.loadMore(targetPage)) {
                is MoreUserListResult.Success -> store.dispatch(
                    LoadMoreAction.Succeed(
                        page = targetPage,
                        result = result.userList
                    )
                )

                is MoreUserListResult.Error -> store.dispatch(
                    LoadMoreAction.Failed(
                        error = result.errorMessage
                    )
                )
            }
        }
    }
}
