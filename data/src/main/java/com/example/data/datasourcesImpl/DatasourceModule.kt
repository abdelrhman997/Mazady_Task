package com.example.data.datasourcesImpl

import com.example.core.services.ApiService
import com.example.data.datasources.CategoryRemoteDatasource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object DatasourceModule {
    @Provides
    fun getCatRemoteDatasource(apiService:ApiService):CategoryRemoteDatasource = CategoryRemoteDatasourceImpl(apiService)
}