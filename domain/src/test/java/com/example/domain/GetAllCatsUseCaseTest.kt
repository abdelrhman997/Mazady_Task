package com.example.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.models.CategoriesItem
import com.example.core.models.CategoriesResponse
import com.example.core.models.Data
import com.example.core.services.NetworkResult
import com.example.domain.repos.CatsRepo
import com.example.domain.usecases.GetAllCatsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetAllCatsUseCaseTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()


    @Mock
    private lateinit var catsRepo: CatsRepo

    lateinit var getAllCatsUseCase: GetAllCatsUseCase

    val catResponse =
        CategoriesResponse(
            data = Data(categories = arrayListOf(CategoriesItem(id = 123)))
        )

    @Before
    fun setUp() {
        getAllCatsUseCase = GetAllCatsUseCase(catsRepo)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetNowPlayingMoviesList() = testCoroutineRule.runTest {
        whenever(catsRepo.getAllCats()).thenAnswer {
            flowOf(
                NetworkResult.Success(
                    catResponse
                )
            )
        }
        val flow = getAllCatsUseCase().first()

        Assert.assertEquals(
            flow.data?.data?.categories?.get(0)?.id,
            catResponse.data?.categories?.get(0)?.id
        )
    }
}