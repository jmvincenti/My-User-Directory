package com.jmvincenti.myuserdirectory.mapper

import com.jmvincenti.myuserdirectory.apiclient.model.ApiCoordinate
import com.jmvincenti.myuserdirectory.apiclient.model.ApiLocation
import com.jmvincenti.myuserdirectory.apiclient.model.ApiUser
import com.jmvincenti.myuserdirectory.model.Coordinate
import com.jmvincenti.myuserdirectory.model.Location
import com.jmvincenti.myuserdirectory.model.Pictures
import com.jmvincenti.myuserdirectory.model.User

fun ApiUser.toModel(fullNameBuilder: UserFullNameBuilder): User = User(
    id = email,
    fullName = fullNameBuilder.build(this),
    email = email,
    phone = phone,
    pictures = Pictures(
        thumbnail = picture.thumbnail,
        cover = picture.large
    ),
    location = location.toModel(),
    cell = cell,
    dob = dob?.date?.time
)

fun ApiLocation.toModel(): Location = Location(
    street = street,
    state = state,
    city = city,
    postcode = postcode,
    coordinate = coordinates?.toModel()
)

fun ApiCoordinate.toModel(): Coordinate = Coordinate(
    lat = latitude,
    long = longitude
)
