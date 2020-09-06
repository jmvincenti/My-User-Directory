package com.jmvincenti.myuserdirectory.database.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DbLocation(
    val street: String,
    val city: String,
    val state: String,
    val postcode: String,
    val lat: String?,
    val long: String?
)
