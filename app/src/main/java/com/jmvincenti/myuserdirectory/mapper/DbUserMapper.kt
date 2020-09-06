package com.jmvincenti.myuserdirectory.mapper

import com.jmvincenti.myuserdirectory.database.model.DbLocation
import com.jmvincenti.myuserdirectory.database.model.DbUser
import com.jmvincenti.myuserdirectory.model.Location
import com.jmvincenti.myuserdirectory.model.Pictures
import com.jmvincenti.myuserdirectory.model.User

fun DbUser.toModel(): User = User(
    id = id,
    fullName = fullName,
    email = email,
    phone = phone,
    cell = null, //TODO
    dob = null, //TODO
    pictures = Pictures(
        thumbnail = thumbnail,
        cover = cover
    ),
    location = location?.toModel()
)

fun DbLocation.toModel(): Location = Location(
    street = street,
    state = state,
    city = city,
    postcode = postcode,
    coordinate = null //TODO
)

fun User.toDb(position: Int): DbUser = DbUser(
    id = id,
    fullName = fullName,
    email = email,
    phone = phone,
    thumbnail = pictures?.thumbnail,
    cover = pictures?.cover,
    location = location?.toDb(),
    position = position.toLong()
)

fun Location.toDb(): DbLocation = DbLocation(
    street = street,
    state = state,
    city = city,
    postcode = postcode
)
