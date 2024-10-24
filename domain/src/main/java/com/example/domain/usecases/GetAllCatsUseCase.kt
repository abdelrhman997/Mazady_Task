package com.example.domain.usecases

import com.example.domain.repos.CatsRepo

class GetAllCatsUseCase(private val catsRepo: CatsRepo) {
    suspend operator fun invoke() = catsRepo.getAllCats()
}