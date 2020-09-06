package com.jmvincenti.myuserdirectory.model

data class Location(
    val street: String,
    val city: String,
    val state: String,
    val postcode: String,
    val coordinate: Coordinate?
)

data class Coordinate(
    val lat: String,
    val long: String
)
