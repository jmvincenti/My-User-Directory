package com.jmvincenti.myuserdirectory.feature.userprofile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jmvincenti.myuserdirectory.feature.userprofile.domain.UserProfileUseCase
import com.jmvincenti.myuserdirectory.feature.userprofile.model.UserProfileCommand
import com.jmvincenti.myuserdirectory.feature.userprofile.model.UserProfileState
import com.jmvincenti.myuserdirectory.feature.userprofile.store.UserProfileStore
import java.util.concurrent.Executor
import javax.inject.Inject

class UserProfileViewModel @ViewModelInject constructor(
    useCase: UserProfileUseCase,
    executor: Executor
) : ViewModel() {

    private val store = UserProfileStore(
        scope = viewModelScope,
        useCase = useCase,
        executor = executor
    )

    fun state(): LiveData<UserProfileState> = store.state

    fun onCommand(command: UserProfileCommand) {
        store.dispatch(command)
    }
}
