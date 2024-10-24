package com.example.domain.usecases

import com.example.domain.repos.CatsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object UseCaseModule {
    @Provides
    fun providesGetAllCatsUseCase(catsRepo: CatsRepo) = GetAllCatsUseCase(catsRepo)
    @Provides
    fun providesGetAllOptionsUseCase(catsRepo: CatsRepo) = GetAllOptionsUseCase(catsRepo)
    @Provides
    fun providesGetCatPropertiesUseCase(catsRepo: CatsRepo) = GetCatPropertiesUseCase(catsRepo)
   }