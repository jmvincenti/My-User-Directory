package com.jmvincenti.myuserdirectory.database.di

import android.content.Context
import com.jmvincenti.myuserdirectory.database.adapter.DbLocationAdapter
import com.jmvincenti.myuserdirectory.database.model.DbUser
import com.jmvincenti.myuserdirectory.db.UserDb
import com.squareup.moshi.Moshi
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

fun provideDriver(context: Context): SqlDriver =
    AndroidSqliteDriver(UserDb.Schema, context, "test.db")

fun provideDbMoshi(): Moshi =
    Moshi.Builder()
        .build()


fun provideUserDb(context: Context): UserDb =
    UserDb(
        driver = provideDriver(context),
        DbUserAdapter = DbUser.Adapter(
            locationAdapter = DbLocationAdapter(provideDbMoshi())
        )
    )
