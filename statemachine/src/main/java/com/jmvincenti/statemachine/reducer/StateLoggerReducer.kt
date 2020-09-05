package com.jmvincenti.statemachine.reducer

import com.jmvincenti.statemachine.Action
import com.jmvincenti.statemachine.Reducer
import com.jmvincenti.statemachine.State

/**
 * Log the current state with the given prefix
 */
open class StateLoggerReducer<S : State, A : Action>(
    private val log: (String, String) -> Unit,
    private val prefix: String = ""
) : Reducer<S, A> {
    override fun invoke(state: S, action: A): S {
        log(LOG_TAG, "$prefix${state.javaClass.simpleName} ($state)")
        return state
    }
}

/**
 * Use those two reducers to log states before and after the mutation by reducers
 */
class BeforeReduceLoggerReducer<S : State, A : Action>(
    log: (String, String) -> Unit
) : StateLoggerReducer<S, A>(
    log = log,
    prefix = "  [M]x[R]  > State   : "
)

class AfterReduceLoggerReducer<S : State, A : Action>(
    log: (String, String) -> Unit
) : StateLoggerReducer<S, A>(
    log = log,
    prefix = "  [M] [R]x > State   : "
)

private const val LOG_TAG = "State"
