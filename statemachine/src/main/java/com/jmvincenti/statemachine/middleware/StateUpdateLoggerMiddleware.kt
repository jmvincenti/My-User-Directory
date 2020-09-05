package com.jmvincenti.statemachine.middleware

import com.jmvincenti.statemachine.*

/**
 * Log actions, previous and new updated states by this action.
 *
 * Use this middleware on top of the middleware list to see the full impact of a dispatched action
 * on the store (middleware and reducers).
 *
 */
class StateUpdateLoggerMiddleware<S : State, A : Action>(
    private val log: (String, String) -> Unit
) : Middleware<S, A> {

    override fun invoke(action: A, store: Store<S, A>, next: Next<A>) {
        val previousState = store.currentState
        next(action)
        val nextState = store.currentState

        if (previousState == nextState) {
            log(LOG_TAG, "---------------")
            log(LOG_TAG, "--> Action    : ${action.javaClass.simpleName} ($action)")
            log(LOG_TAG, "<-- No state change")
            log(LOG_TAG, "---------------")

        } else {
            log(LOG_TAG, "---------------")
            log(LOG_TAG, "--> Action    : ${action.javaClass.simpleName} ($action)")
            log(
                LOG_TAG,
                "<-- New state : ${store.currentState.javaClass.simpleName} (${store.currentState})"
            )
            log(LOG_TAG, "---------------")
        }
    }
}

private const val LOG_TAG = "State"
