package com.jmvincenti.myuserdirectory.database.di

import android.content.Context
import com.jmvincenti.myuserdirectory.database.adapter.DbLocationAdapter
import com.jmvincenti.myuserdirectory.database.model.DbUser
import com.jmvincenti.myuserdirectory.db.UserDb
import com.squareup.moshi.Moshi
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DbMoshi

@Module
@InstallIn(ApplicationComponent::class)
object DbModule {

    @Provides
    @DbMoshi
    fun providesDbMoshi(): Moshi =
        Moshi.Builder()
            // Add custom Json adapter here for database storage
            .build()

    @Provides
    fun providesUserDb(
        @ApplicationContext context: Context,
        @DbMoshi dbMoshi: Moshi
    ): UserDb = UserDb(
        driver = AndroidSqliteDriver(UserDb.Schema, context, "test.db"),
        DbUserAdapter = DbUser.Adapter(
            locationAdapter = DbLocationAdapter(dbMoshi)
        )
    )
}
