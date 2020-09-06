package com.jmvincenti.myuserdirectory.apiclient

import com.jmvincenti.myuserdirectory.apiclient.model.ApiUserResult
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("/api/1.2/")
    suspend fun getUsers(
        @Query("seed") seed: String,
        @Query("results") results: Int,
        @Query("page") page: Int,
    ): ApiUserResult
}

fun Retrofit.userService(): UserService = create(UserService::class.java)
