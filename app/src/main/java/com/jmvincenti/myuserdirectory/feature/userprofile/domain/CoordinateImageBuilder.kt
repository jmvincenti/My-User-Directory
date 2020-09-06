package com.jmvincenti.myuserdirectory.feature.userprofile.domain

import com.jmvincenti.myuserdirectory.model.Coordinate

interface CoordinateImageBuilder {
    fun build(coordinate: Coordinate): String
}
