package com.jmvincenti.myuserdirectory.feature.userprofile.model

import com.jmvincenti.myuserdirectory.model.User
import com.jmvincenti.statemachine.State

data class UserProfileState(
    val loadState: LoadUserState,
    val user: User?
) : State


sealed class LoadUserState : State {

    override fun toString(): String = "LoadUserState"

    object Idle : LoadUserState() {
        override fun toString(): String = "${super.toString()}.Idle"
    }

    object Loading : LoadUserState() {
        override fun toString(): String = "${super.toString()}.Loading"
    }

    object Error : LoadUserState() {
        override fun toString(): String = "${super.toString()}.Error"
    }
}
