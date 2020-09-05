package com.jmvincenti.myuserdirectory.feature.userlist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object StoreModule {

    @Provides
    fun provideExecutor(): Executor = Executors.newSingleThreadExecutor()
}
