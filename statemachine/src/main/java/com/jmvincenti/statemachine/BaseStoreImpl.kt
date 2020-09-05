package com.jmvincenti.statemachine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.Executor
import kotlin.properties.Delegates

abstract class BaseStoreImpl<S : State, A : Action>(
        initialState: S
) : Store<S, A> {

    abstract val executor: Executor
    abstract val middlewares: List<Middleware<S, A>>
    abstract val reducers: List<Reducer<S, A>>

    private var _state = MutableLiveData<S>(initialState)
    private var _currentState: S by Delegates.observable(initialState) { _, oldValue, newValue ->
        if (oldValue != newValue) {
            _state.postValue(newValue)
        }
    }

    override val state: LiveData<S> = _state
    override val currentState: S
        get() = _currentState

    override fun dispatch(action: A) {
        executor.execute {
            applyMiddleware(action)
        }
    }

    private fun applyReducers(action: A) {
        var state = _currentState
        for (reducer in reducers) {
            state = reducer(state, action)
        }

        _currentState = state
    }

    private fun applyMiddleware(action: A) {
        val nextOperation = nextMiddleware(0)
        nextOperation(action)
    }

    /**
     * Return a function to call the next middleware with an action.
     *
     * If the middleware is the last of the list, apply reducers to the current state and given
     * action
     */
    private fun nextMiddleware(index: Int): Next<A> {
        if (index == middlewares.size) {
            // Last link of the chain. Apply reducers
            return { action -> applyReducers(action) }
        }

        return { action ->
            middlewares[index](
                    action,
                    this@BaseStoreImpl,
                    nextMiddleware(index + 1)
            )
        }
    }
}
