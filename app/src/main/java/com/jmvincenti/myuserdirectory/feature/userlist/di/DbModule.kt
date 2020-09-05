package com.jmvincenti.myuserdirectory.feature.userlist.di

import android.content.Context
import com.jmvincenti.myuserdirectory.database.di.provideUserDb
import com.jmvincenti.myuserdirectory.db.UserDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
object DbModule {

    @Provides
    fun provideUserDataBase(
        @ApplicationContext context: Context
    ): UserDb = provideUserDb(context)
}
