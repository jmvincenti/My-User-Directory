package com.jmvincenti.myuserdirectory.feature.userprofile.model

import com.jmvincenti.myuserdirectory.model.User
import com.jmvincenti.statemachine.Action

interface UserProfileAction : Action

// UserProfile UI actions
sealed class UserProfileCommand : UserProfileAction {
    data class Init(val userId: String) : UserProfileCommand()
}

// UserProfile StateMachine actions
sealed class LoadAction : UserProfileAction {
    object Started : LoadAction()

    data class Succeed(
        val result: User
    ) : LoadAction()

    object Failed : LoadAction()
}
