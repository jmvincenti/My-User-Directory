package com.jmvincenti.myuserdirectory.feature.userlist.domain

import com.jmvincenti.myuserdirectory.model.User

class MockedLocalDataSource(
    var cachedList: List<User> = emptyList()
) : UserListLocalDataSource {

    override fun clear() {
        cachedList = emptyList()
    }

    override fun queryAll(): List<User> = cachedList

    override fun save(offset: Int, userList: List<User>) {
        check(offset == cachedList.size)
        cachedList = cachedList + userList
    }
}
