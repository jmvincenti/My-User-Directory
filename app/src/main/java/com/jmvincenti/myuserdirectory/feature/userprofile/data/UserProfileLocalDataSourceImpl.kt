package com.jmvincenti.myuserdirectory.feature.userprofile.data

import com.jmvincenti.myuserdirectory.db.UserDb
import com.jmvincenti.myuserdirectory.feature.userprofile.domain.UserProfileLocalDataSource
import com.jmvincenti.myuserdirectory.mapper.toModel
import com.jmvincenti.myuserdirectory.model.User
import javax.inject.Inject

class UserProfileLocalDataSourceImpl @Inject constructor(
    private val database: UserDb
) : UserProfileLocalDataSource {
    override fun getUser(userId: String): User? =
        database.dbUserQueries
            .selectById(userId)
            .executeAsOneOrNull()
            ?.toModel()
}
