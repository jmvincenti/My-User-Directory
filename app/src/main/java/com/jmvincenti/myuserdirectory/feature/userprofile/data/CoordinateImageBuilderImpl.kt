package com.jmvincenti.myuserdirectory.feature.userprofile.data

import android.content.Context
import com.jmvincenti.myuserdirectory.R
import com.jmvincenti.myuserdirectory.feature.userprofile.domain.CoordinateImageBuilder
import com.jmvincenti.myuserdirectory.model.Coordinate
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CoordinateImageBuilderImpl @Inject constructor(
    @ApplicationContext context: Context
) : CoordinateImageBuilder {
    private val apiKey = context.getString(R.string.goeapify_apikey)

    override fun build(coordinate: Coordinate): String =
        "https://maps.geoapify.com/v1/staticmap?" +
                "style=klokantech-basic" +
                "&width=400&height=600" +
                "&center=lonlat:${coordinate.long},${coordinate.lat}" +
                "&zoom=5" +
                "&apiKey=$apiKey"
}
