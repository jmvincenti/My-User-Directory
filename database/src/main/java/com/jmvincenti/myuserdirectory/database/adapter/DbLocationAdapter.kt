package com.jmvincenti.myuserdirectory.database.adapter

import com.jmvincenti.myuserdirectory.database.model.DbLocation
import com.squareup.moshi.Moshi
import com.squareup.sqldelight.ColumnAdapter

class DbLocationAdapter(moshi: Moshi) : ColumnAdapter<DbLocation, String> {

    private val moshiAdapter = moshi.adapter<DbLocation>(DbLocation::class.java)

    override fun decode(databaseValue: String): DbLocation =
        requireNotNull(moshiAdapter.fromJson(databaseValue))

    override fun encode(value: DbLocation): String =
        moshiAdapter.toJson(value)
}
