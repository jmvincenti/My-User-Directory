package com.jmvincenti.statemachine

import java.util.*

sealed class SimpleLoadingState : State {

    override fun toString(): String = "SimpleLoadingState"

    object Idle : SimpleLoadingState() {
        override fun toString(): String = "${super.toString()}.Idle"
    }

    object Loading : SimpleLoadingState() {
        override fun toString(): String = "${super.toString()}.Loading"
    }

    data class Error(val errorMessage: ErrorMessage) : SimpleLoadingState() {
        override fun toString(): String = "${super.toString()}.Error(errorMessage=$errorMessage"
    }
}

data class ErrorMessage(
    val id: String = UUID.randomUUID().toString(),
    val message: String
)
