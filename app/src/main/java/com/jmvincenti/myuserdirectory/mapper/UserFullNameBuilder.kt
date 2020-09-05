package com.jmvincenti.myuserdirectory.mapper

import android.content.Context
import com.jmvincenti.myuserdirectory.R
import com.jmvincenti.myuserdirectory.apiclient.model.ApiUser

class UserFullNameBuilder(private val context: Context) {

    fun build(user: ApiUser): String = context.getString(
        R.string.full_name_template,
        user.name.title,
        user.name.first,
        user.name.last
    )
}
