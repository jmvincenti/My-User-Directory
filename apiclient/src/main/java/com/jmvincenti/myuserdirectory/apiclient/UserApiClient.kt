package com.jmvincenti.myuserdirectory.apiclient

import com.jmvincenti.myuserdirectory.apiclient.model.ApiUser
import retrofit2.Retrofit
import javax.inject.Inject

class UserApiClient @Inject constructor(
    retrofit: Retrofit
) {
    private val service: UserService = retrofit.userService()

    suspend fun getUsers(page: Int, pageSize: Int): List<ApiUser> = service
        .getUsers(
            seed = "Lydia",
            page = page,
            results = pageSize
        )
        .results
}
