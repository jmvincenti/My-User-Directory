package com.jmvincenti.statemachine

sealed class SimpleLoadingAction : State {
    object Started : SimpleLoadingAction()
    object Succeed : SimpleLoadingAction()
    data class Failed(val error: String) : SimpleLoadingAction()
}
