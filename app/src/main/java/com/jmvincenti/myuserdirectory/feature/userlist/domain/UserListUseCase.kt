package com.jmvincenti.myuserdirectory.feature.userlist.domain

import com.jmvincenti.myuserdirectory.feature.userlist.model.InitialUserListResult
import com.jmvincenti.myuserdirectory.feature.userlist.model.MoreUserListResult
import javax.inject.Inject

class UserListUseCase @Inject constructor(
    private val remoteDataSource: UserListRemoteDataSource,
    private val localDataSource: UserListLocalDataSource
) {

    suspend fun loadInitial(): InitialUserListResult =
        when (val result = remoteDataSource.loadInitial(PAGE_SIZE)) {
            is UserListRemoteResult.Success -> {
                localDataSource.clear()
                localDataSource.save(0, result.userList)
                InitialUserListResult.Success(result.userList)
            }

            is UserListRemoteResult.Error -> {
                val cachedList = localDataSource.queryAll()
                InitialUserListResult.Error(
                    errorMessage = result.error,
                    cachedList = cachedList
                )
            }
        }

    suspend fun loadMore(page: Int): MoreUserListResult =
        when (val result = remoteDataSource.loadMore(page, PAGE_SIZE)) {
            is UserListRemoteResult.Success -> {
                localDataSource.save(page * PAGE_SIZE, result.userList)
                MoreUserListResult.Success(result.userList)
            }

            is UserListRemoteResult.Error -> {
                MoreUserListResult.Error(
                    errorMessage = result.error
                )
            }
        }
}

private const val PAGE_SIZE = 10
