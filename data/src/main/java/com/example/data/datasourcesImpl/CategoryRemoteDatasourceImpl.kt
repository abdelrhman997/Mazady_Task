package com.example.data.datasourcesImpl

import com.example.core.models.CategoriesResponse
import com.example.core.models.CategoryPropertiesResponse
import com.example.core.services.ApiService
import com.example.data.datasources.CategoryRemoteDatasource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response
import javax.inject.Inject

@ActivityRetainedScoped
class CategoryRemoteDatasourceImpl @Inject constructor(val apiService: ApiService) : CategoryRemoteDatasource {
    override suspend fun getAllCats(): Response<CategoriesResponse> = apiService.getAllCats()

    override suspend fun getCatProperties(catId: Int): Response<CategoryPropertiesResponse> = apiService.getCatProperties(catId)

    override suspend fun getAllOptions(
        id: Int,
    ): Response<CategoryPropertiesResponse> = apiService.getAllOptions(id)
}