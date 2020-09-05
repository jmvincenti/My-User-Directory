package com.jmvincenti.myuserdirectory.mapper

import android.content.Context
import com.jmvincenti.myuserdirectory.R
import com.jmvincenti.myuserdirectory.apiclient.model.ApiUser
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserFullNameBuilder @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun build(user: ApiUser): String = context.getString(
        R.string.full_name_template,
        user.name.title,
        user.name.first,
        user.name.last
    ).split(" ").joinToString(" ") { it.capitalize() }
}
