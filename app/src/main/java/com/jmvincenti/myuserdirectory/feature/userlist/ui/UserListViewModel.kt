package com.jmvincenti.myuserdirectory.feature.userlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jmvincenti.myuserdirectory.feature.userlist.domain.UserListUseCase
import com.jmvincenti.myuserdirectory.feature.userlist.model.UserListCommand
import com.jmvincenti.myuserdirectory.feature.userlist.model.UserListState
import com.jmvincenti.myuserdirectory.feature.userlist.store.UserListStore
import java.util.concurrent.Executor
import javax.inject.Inject

class UserListViewModel(
    useCase: UserListUseCase,
    executor: Executor
) : ViewModel() {

    private val store = UserListStore(
        scope = viewModelScope,
        useCase = useCase,
        executor = executor
    )

    init {
        store.dispatch(UserListCommand.OnStart)
    }

    fun state(): LiveData<UserListState> = store.state

    fun onCommand(command: UserListCommand) {
        store.dispatch(command)
    }

    class Factory @Inject constructor(
        private val useCase: UserListUseCase,
        private val executor: Executor
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            UserListViewModel(useCase, executor) as T
    }
}
