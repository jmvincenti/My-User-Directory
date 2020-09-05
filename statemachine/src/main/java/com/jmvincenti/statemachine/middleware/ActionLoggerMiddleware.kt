package com.jmvincenti.statemachine.middleware

import com.jmvincenti.statemachine.*

/**
 * Log the action with the given prefix
 */
open class ActionLoggerMiddleware<S : State, A : Action>(
    private val log: (String, String) -> Unit,
    private val prefix: String = ""
) : Middleware<S, A> {

    override fun invoke(action: A, store: Store<S, A>, next: Next<A>) {
        log(LOG_TAG, "$prefix${action.javaClass.simpleName} ($action)")
        next(action)
    }
}

/**
 * Use those two middleware to log actions received by the first and the last key of the
 * middleware chain
 *
 * This will be useful to have a visible tree of action mutation/generation across the middleware
 * chain
 */
class BeforeChainLoggerMiddleware<S : State, A : Action>(
    log: (String, String) -> Unit
) : ActionLoggerMiddleware<S, A>(
    log = log,
    prefix = " x[M] [R]  > Action  : "
)

class AfterChainLoggerMiddleware<S : State, A : Action>(
    log: (String, String) -> Unit
) : ActionLoggerMiddleware<S, A>(
    log = log,
    prefix = "  [M]x[R]  > Action  : "
)

private const val LOG_TAG = "State"
