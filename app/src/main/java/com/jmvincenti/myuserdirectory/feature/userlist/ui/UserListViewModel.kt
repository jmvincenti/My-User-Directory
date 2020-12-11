package com.jmvincenti.myuserdirectory.feature.userlist.ui

import androidx.hilt.lifecycle.ViewModelInject
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

class UserListViewModel @ViewModelInject constructor(
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
}
