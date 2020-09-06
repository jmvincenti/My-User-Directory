package com.jmvincenti.myuserdirectory.model

import java.util.*

data class User(
    val id: String,
    val fullName: String,
    val email: String?,
    val phone: String?,
    val cell: String?,
    val dob: Date?,
    val pictures: Pictures?,
    val location: Location?
)
