package com.jmvincenti.myuserdirectory.database.di

import com.jmvincenti.myuserdirectory.database.adapter.DbLocationAdapter
import com.jmvincenti.myuserdirectory.database.model.DbUser
import com.jmvincenti.myuserdirectory.db.UserDb
import com.squareup.moshi.Moshi
import com.squareup.sqldelight.db.SqlDriver

fun provideUserDb(driver: SqlDriver, moshi: Moshi) : UserDb =
    UserDb(
        driver = driver,
        DbUserAdapter = DbUser.Adapter(
            locationAdapter = DbLocationAdapter(moshi)
        )
    )
