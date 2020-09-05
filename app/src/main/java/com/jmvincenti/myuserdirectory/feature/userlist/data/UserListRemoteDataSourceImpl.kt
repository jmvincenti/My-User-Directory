package com.jmvincenti.myuserdirectory.feature.userlist.data

import android.content.Context
import com.jmvincenti.myuserdirectory.R
import com.jmvincenti.myuserdirectory.apiclient.UserApiClient
import com.jmvincenti.myuserdirectory.apiclient.model.ResultWrapper
import com.jmvincenti.myuserdirectory.apiclient.safeApiCall
import com.jmvincenti.myuserdirectory.feature.userlist.domain.UserListRemoteDataSource
import com.jmvincenti.myuserdirectory.feature.userlist.domain.UserListRemoteResult
import com.jmvincenti.myuserdirectory.mapper.UserFullNameBuilder
import com.jmvincenti.myuserdirectory.mapper.toModel
import com.jmvincenti.statemachine.ErrorMessage
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserListRemoteDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiClient: UserApiClient,
    private val fullNameBuilder: UserFullNameBuilder
) : UserListRemoteDataSource {

    override suspend fun loadInitial(pageSize: Int): UserListRemoteResult =
        load(1, pageSize)

    override suspend fun loadMore(page: Int, pageSize: Int): UserListRemoteResult =
        load(page, pageSize)

    private suspend fun load(page: Int, pageSize: Int): UserListRemoteResult {
        val result = safeApiCall {
            apiClient.getUsers(page = page, pageSize = pageSize)
        }

        return when (result) {
            is ResultWrapper.Success -> UserListRemoteResult.Success(
                userList = result.value.map { it.toModel(fullNameBuilder) }
            )

            is ResultWrapper.GenericError -> UserListRemoteResult.Error(
                error = ErrorMessage(
                    message = result.error
                        ?: context.getString(R.string.generic_error)
                )
            )

            ResultWrapper.NetworkError -> UserListRemoteResult.Error(
                error = ErrorMessage(
                    message = context.getString(R.string.generic_error)
                )
            )
        }
    }
}
