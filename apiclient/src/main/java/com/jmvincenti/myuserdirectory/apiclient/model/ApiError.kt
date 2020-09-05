package com.jmvincenti.myuserdirectory.apiclient.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiError(
    val error: String
)
