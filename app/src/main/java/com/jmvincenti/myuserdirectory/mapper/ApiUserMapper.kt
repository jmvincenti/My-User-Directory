package com.jmvincenti.myuserdirectory.mapper

import com.jmvincenti.myuserdirectory.apiclient.model.ApiLocation
import com.jmvincenti.myuserdirectory.apiclient.model.ApiUser
import com.jmvincenti.myuserdirectory.model.Location
import com.jmvincenti.myuserdirectory.model.Pictures
import com.jmvincenti.myuserdirectory.model.User
import java.util.*

fun ApiUser.toModel(fullNameBuilder: UserFullNameBuilder): User = User(
    id = email,
    fullName = fullNameBuilder.build(this),
    email = email,
    phone = phone,
    pictures = Pictures(
        thumbnail = picture.thumbnail,
        cover = picture.large
    ),
    location = location.toModel()
)

fun ApiLocation.toModel(): Location = Location(
    street = street,
    state = state,
    city = city,
    postcode = postcode
)
