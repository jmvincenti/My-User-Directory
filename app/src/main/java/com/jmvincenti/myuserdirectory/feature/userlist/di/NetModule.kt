package com.jmvincenti.myuserdirectory.feature.userlist.di

import com.jmvincenti.myuserdirectory.apiclient.UserApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object NetModule {

    @Provides
    fun provideApiClient(): UserApiClient = UserApiClient()
}
