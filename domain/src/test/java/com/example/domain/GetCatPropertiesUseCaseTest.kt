package com.example.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.models.*
import com.example.core.services.NetworkResult
import com.example.domain.repos.CatsRepo
import com.example.domain.usecases.GetAllCatsUseCase
import com.example.domain.usecases.GetCatPropertiesUseCase
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
class GetCatPropertiesUseCaseTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()


    @Mock
    private lateinit var catsRepo: CatsRepo

    lateinit var getCatPropertiesUseCase: GetCatPropertiesUseCase

    val tCatId = 1

    val catPropertiesResponse =
        CategoryPropertiesResponse(
            data = arrayListOf(CategoryProperty(id = 123))
        )

    @Before
    fun setUp() {
        getCatPropertiesUseCase = GetCatPropertiesUseCase(catsRepo)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetNowPlayingMoviesList() = testCoroutineRule.runTest {
        whenever(catsRepo.getCatProperties(tCatId)).thenAnswer {
            flowOf(
                NetworkResult.Success(
                    catPropertiesResponse
                )
            )
        }
        val flow = getCatPropertiesUseCase(tCatId).first()

        Assert.assertEquals(flow.data?.data?.get(0)?.id, catPropertiesResponse.data?.get(0)?.id)
    }
}