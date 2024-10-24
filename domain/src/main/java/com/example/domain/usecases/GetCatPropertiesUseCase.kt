package com.example.domain.usecases

import com.example.domain.repos.CatsRepo

class GetCatPropertiesUseCase(private val catsRepo: CatsRepo) {
    suspend operator fun invoke(catId: Int) = catsRepo.getCatProperties(catId)
}