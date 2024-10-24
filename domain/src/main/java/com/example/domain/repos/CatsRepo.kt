package com.example.domain.repos

import com.example.core.models.CategoriesResponse
import com.example.core.models.CategoryPropertiesResponse
import com.example.core.services.NetworkResult
import kotlinx.coroutines.flow.Flow

interface CatsRepo {
    suspend fun getAllCats() : Flow<NetworkResult<CategoriesResponse>>
    suspend fun getCatProperties(catId:Int) : Flow<NetworkResult<CategoryPropertiesResponse>>
    suspend fun getAllOptions(id:Int) : Flow<NetworkResult<CategoryPropertiesResponse>>
}