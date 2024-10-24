package com.example.core.services

import com.example.core.models.CategoriesResponse
import com.example.core.models.CategoryPropertiesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("get_all_cats")
    suspend fun getAllCats(): Response<CategoriesResponse>

    @GET("properties")
    suspend fun getCatProperties(@Query("cat") catId: Int): Response<CategoryPropertiesResponse>

    @GET("get-options-child/{id}")
    suspend fun getAllOptions(@Path("id") id: Int): Response<CategoryPropertiesResponse>

}