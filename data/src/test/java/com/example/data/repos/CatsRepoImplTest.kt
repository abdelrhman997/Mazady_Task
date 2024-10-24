package com.example.data.repos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.models.*
import com.example.data.datasources.CategoryRemoteDatasource
import com.example.domain.repos.CatsRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class CatsRepoImplTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var catsRemoteDatasource: CategoryRemoteDatasource

    lateinit var repo: CatsRepo

    val catResponse =
        CategoriesResponse(
            data = Data(categories = arrayListOf(CategoriesItem(id = 123)))
        )

    val catPropertiesResponse =
        CategoryPropertiesResponse(
            data = arrayListOf(CategoryProperty(id = 123))
        )
    val tCatId = 1

    @Before
    fun setUp() {
        repo = CatsRepoImpl(catsRemoteDatasource)
    }

    @Test
    fun getAllCats() = testCoroutineRule.runTest {
        whenever(catsRemoteDatasource.getAllCats()).thenAnswer {
            Response.success(
                catResponse
            )
        }

        val result = repo.getAllCats().first()

        Assert.assertEquals(
            result.data?.data?.categories?.get(0)?.id,
            catResponse.data?.categories?.get(0)?.id
        )
    }

    @Test
    fun getCatProperties() = testCoroutineRule.runTest {
        whenever(catsRemoteDatasource.getCatProperties(tCatId)).thenAnswer {
            Response.success(
                catPropertiesResponse
            )
        }

        val result = repo.getCatProperties(tCatId).first()

        Assert.assertEquals(result.data?.data?.get(0)?.id, catPropertiesResponse.data?.get(0)?.id)
    }

    @Test
    fun getAllOptions() = testCoroutineRule.runTest {
        whenever(catsRemoteDatasource.getAllOptions(tCatId)).thenAnswer {
            Response.success(
                catPropertiesResponse
            )
        }

        val result = repo.getAllOptions(tCatId).first()

        Assert.assertEquals(result.data?.data?.get(0)?.id, catPropertiesResponse.data?.get(0)?.id)
    }
}