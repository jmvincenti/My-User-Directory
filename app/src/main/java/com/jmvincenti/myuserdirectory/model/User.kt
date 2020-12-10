package com.jmvincenti.myuserdirectory.model

data class User(
    val id: String,
    val fullName: String,
    val email: String?,
    val phone: String?,
    val cell: String?,
    val dob: Long?,
    val pictures: Pictures?,
    val location: Location?
)
