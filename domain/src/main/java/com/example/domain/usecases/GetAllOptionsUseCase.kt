package com.example.domain.usecases

import com.example.domain.repos.CatsRepo

class GetAllOptionsUseCase(private val catsRepo: CatsRepo) {
    suspend operator fun invoke(id: Int) = catsRepo.getAllOptions(id)
}