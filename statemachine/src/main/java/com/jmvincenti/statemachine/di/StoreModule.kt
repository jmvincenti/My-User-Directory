package com.jmvincenti.statemachine.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object StoreModule {

    @Provides
    @Singleton
    fun provideExecutor(): Executor = Executors.newSingleThreadExecutor()
}
