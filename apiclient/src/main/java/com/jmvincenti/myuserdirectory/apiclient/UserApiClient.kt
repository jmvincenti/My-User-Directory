package com.jmvincenti.myuserdirectory.apiclient

import com.jmvincenti.myuserdirectory.apiclient.model.ApiUser
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class UserApiClient(
    private val baseUrl: String = "https://randomuser.me/api/1.0/",
    private val seed: String = "Lydia"
) {

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val service = retrofit.userService()

    suspend fun getUsers(page: Int, pageSize: Int): List<ApiUser> = service
        .getUsers(
            seed = seed,
            page = page,
            results = pageSize
        )
        .results
}
