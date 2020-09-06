package com.jmvincenti.myuserdirectory.apiclient.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiUser(
    val id: ApiId,
    val gender: String,
    val name: ApiUserName,
    val email: String,
    val phone: String,
    val cell: String,
    val dob: Long,
    val picture: ApiPicture,
    val nat: String,
    val location: ApiLocation,
)

@JsonClass(generateAdapter = true)
data class ApiPicture(
    val large: String,
    val thumbnail: String
)

@JsonClass(generateAdapter = true)
data class ApiId(
    val name: String?,
    val value: String?
)

@JsonClass(generateAdapter = true)
data class ApiUserName(
    val title: String,
    val first: String,
    val last: String,
)

@JsonClass(generateAdapter = true)
data class ApiLocation(
    val street: String,
    val city: String,
    val state: String,
    val postcode: String,
    val coordinates: ApiCoordinate?
)

@JsonClass(generateAdapter = true)
data class ApiCoordinate(
    val latitude: String,
    val longitude: String
)
