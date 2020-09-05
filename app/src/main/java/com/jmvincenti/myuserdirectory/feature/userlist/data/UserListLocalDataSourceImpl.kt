package com.jmvincenti.myuserdirectory.feature.userlist.data

import com.jmvincenti.myuserdirectory.db.UserDb
import com.jmvincenti.myuserdirectory.feature.userlist.domain.UserListLocalDataSource
import com.jmvincenti.myuserdirectory.mapper.toDb
import com.jmvincenti.myuserdirectory.mapper.toModel
import com.jmvincenti.myuserdirectory.model.User

class UserListLocalDataSourceImpl(
    private val database: UserDb
) : UserListLocalDataSource {
    override fun clear() {
        database.dbUserQueries.deleteAll()
    }

    override fun queryAll(): List<User> =
        database.dbUserQueries.selectAll()
            .executeAsList()
            .map { it.toModel() }

    override fun save(offset: Int, userList: List<User>) {
        database.transaction {
            userList.forEachIndexed { index, user ->
                database.dbUserQueries.insert(
                    user.toDb(offset + index)
                )
            }
        }
    }
}
