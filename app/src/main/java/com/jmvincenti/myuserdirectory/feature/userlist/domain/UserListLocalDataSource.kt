package com.jmvincenti.myuserdirectory.feature.userlist.domain

import com.jmvincenti.myuserdirectory.model.User

interface UserListLocalDataSource {
    fun clear()
    fun queryAll(): List<User>
    fun save(offset: Int, userList: List<User>)
}
