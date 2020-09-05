package com.jmvincenti.myuserdirectory.feature.userlist.store

import com.jmvincenti.myuserdirectory.feature.userlist.domain.FailingRemoteDataSource
import com.jmvincenti.myuserdirectory.feature.userlist.domain.MockedLocalDataSource
import com.jmvincenti.myuserdirectory.feature.userlist.domain.SucceedRemoteDataSource
import com.jmvincenti.myuserdirectory.feature.userlist.domain.UserListUseCase
import com.jmvincenti.myuserdirectory.feature.userlist.model.UserListCommand
import com.jmvincenti.myuserdirectory.feature.userlist.model.UserListState
import com.jmvincenti.myuserdirectory.model.User
import com.jmvincenti.statemachine.ErrorMessage
import com.jmvincenti.statemachine.SimpleLoadingState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.concurrent.Executor

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class UserListStoreTest {

    private val testScope = TestCoroutineScope()

    @Test
    fun `failing initial call display cached list`() {
        // Given
        val expectedError = ErrorMessage(message = "Failed")
        val expectedUserList = listOf(
            User(
                id = "0",
                fullName = "User0",
                email = null,
                location = null,
                phone = null,
                pictures = null
            ),
            User(
                id = "1",
                fullName = "User1",
                email = null,
                location = null,
                phone = null,
                pictures = null
            )
        )

        val store = UserListStore(
            scope = testScope,
            executor = Executor { it.run() },
            useCase = UserListUseCase(
                remoteDataSource = FailingRemoteDataSource(expectedError),
                localDataSource = MockedLocalDataSource(
                    cachedList = expectedUserList
                )
            )
        )

        // When
        store.dispatch(UserListCommand.OnStart)

        // Then
        assert(
            store.currentState == UserListState(
                currentPage = 0,
                userList = expectedUserList,
                loadInitialState = SimpleLoadingState.Error(expectedError),
                loadMoreState = SimpleLoadingState.Idle
            )
        )
    }

    @Test
    fun `retry initial with cached result display new list and replace cached values`() {
        // Given
        val cachedList = listOf(
            User(
                id = "A0",
                fullName = "UserA0",
                email = null,
                location = null,
                phone = null,
                pictures = null
            ),
            User(
                id = "A1",
                fullName = "UserA1",
                email = null,
                location = null,
                phone = null,
                pictures = null
            )
        )
        val newList = listOf(
            User(
                id = "B0",
                fullName = "UserB0",
                email = null,
                location = null,
                phone = null,
                pictures = null
            ),
            User(
                id = "B1",
                fullName = "UserB1",
                email = null,
                location = null,
                phone = null,
                pictures = null
            )
        )

        val localDataSource = MockedLocalDataSource(cachedList = cachedList)

        val store = UserListStore(
            scope = testScope,
            executor = Executor { it.run() },
            useCase = UserListUseCase(
                remoteDataSource = SucceedRemoteDataSource(newList),
                localDataSource = localDataSource
            )
        )

        // When
        store.dispatch(UserListCommand.OnStart)

        // Then
        assert(
            store.currentState == UserListState(
                currentPage = 1,
                userList = newList,
                loadInitialState = SimpleLoadingState.Idle,
                loadMoreState = SimpleLoadingState.Idle
            )
        )
        assert(
            localDataSource.cachedList == newList
        )
    }
}
