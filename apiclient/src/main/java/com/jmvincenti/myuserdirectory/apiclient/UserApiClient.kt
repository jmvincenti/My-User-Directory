package com.jmvincenti.myuserdirectory.apiclient

import com.jmvincenti.myuserdirectory.apiclient.model.ApiUser
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class UserApiClient(
    private val baseUrl: String = "https://randomuser.me",
    private val seed: String = "Lydia"
) {

    private val retrofit: Retrofit


    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private val service = retrofit.userService()

    suspend fun getUsers(page: Int, pageSize: Int): List<ApiUser> = service
        .getUsers(
            seed = seed,
            page = page,
            results = pageSize
        )
        .results
}
