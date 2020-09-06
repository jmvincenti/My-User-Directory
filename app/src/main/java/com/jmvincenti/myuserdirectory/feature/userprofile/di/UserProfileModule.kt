package com.jmvincenti.myuserdirectory.feature.userprofile.di

import com.jmvincenti.myuserdirectory.feature.userprofile.data.CoordinateImageBuilderImpl
import com.jmvincenti.myuserdirectory.feature.userprofile.data.UserProfileLocalDataSourceImpl
import com.jmvincenti.myuserdirectory.feature.userprofile.domain.CoordinateImageBuilder
import com.jmvincenti.myuserdirectory.feature.userprofile.domain.UserProfileLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class UserProfileModule {

    @Binds
    abstract fun bindCoordinateImageBuilder(
        coordinateImageBuilderImpl: CoordinateImageBuilderImpl
    ): CoordinateImageBuilder

    @Binds
    abstract fun bindUserProfileLocalDataSource(
        userProfileLocalDataSourceImpl: UserProfileLocalDataSourceImpl
    ): UserProfileLocalDataSource
}
