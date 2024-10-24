package com.example.data.datasources

import com.example.core.models.CategoriesResponse
import com.example.core.models.CategoryPropertiesResponse
import retrofit2.Response

interface CategoryRemoteDatasource {
    suspend fun getAllCats(): Response<CategoriesResponse>
    suspend fun getCatProperties(catId: Int): Response<CategoryPropertiesResponse>
    suspend fun getAllOptions(id: Int): Response<CategoryPropertiesResponse>
}