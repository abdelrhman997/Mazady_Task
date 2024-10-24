package com.example.data.repos

import com.example.data.datasources.CategoryRemoteDatasource
import com.example.domain.repos.CatsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object RepoModule {
    @Provides
    fun providesCatRepo(categoryRemoteDatasource: CategoryRemoteDatasource):CatsRepo = CatsRepoImpl(categoryRemoteDatasource)
}