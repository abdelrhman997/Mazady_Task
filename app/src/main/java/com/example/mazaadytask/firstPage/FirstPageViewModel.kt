package com.example.mazaadytask.firstPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.models.CategoriesResponse
import com.example.core.models.CategoryPropertiesResponse
import com.example.core.services.NetworkResult
import com.example.domain.usecases.GetAllCatsUseCase
import com.example.domain.usecases.GetAllOptionsUseCase
import com.example.domain.usecases.GetCatPropertiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstPageViewModel @Inject constructor(
    val getAllCatsUseCase: GetAllCatsUseCase,
    val getCatPropertiesUseCase: GetCatPropertiesUseCase,
    val getAllOptionsUseCase: GetAllOptionsUseCase
):ViewModel() {

    private val _mainCatState = MutableStateFlow<NetworkResult<CategoriesResponse>>(NetworkResult.Loading())
    private val _catPropertiesState = MutableStateFlow<NetworkResult<CategoryPropertiesResponse>>(NetworkResult.Loading())
    private val _allOptionsState = MutableStateFlow<NetworkResult<CategoryPropertiesResponse>>(NetworkResult.Loading())

    val mainCatState: StateFlow<NetworkResult<CategoriesResponse>> = _mainCatState
    val catPropertiesState: StateFlow<NetworkResult<CategoryPropertiesResponse>> = _catPropertiesState
    val allOptionsState: StateFlow<NetworkResult<CategoryPropertiesResponse>> = _allOptionsState

    init {
        fetchMainCat()
    }

    private fun fetchMainCat() {
        viewModelScope.launch{
            getAllCatsUseCase()
                .catch { exception -> notifyError(exception) }
                .collect{
                    _mainCatState.value = it
                }
        }
    }

    fun fetchCatProperties(catId:Int) {
        viewModelScope.launch{
            getCatPropertiesUseCase(catId)
                .catch { exception -> notifyError(exception) }
                .collect{
                    Log.d("viewModelScope","Success _catPropertiesState")
                    _catPropertiesState.value = it
                }
        }
    }

    fun fetchAllOptions(id:Int) {
        viewModelScope.launch{
            getAllOptionsUseCase(id)
                .catch { exception -> notifyError(exception) }
                .collect{
                    _allOptionsState.value = it
                }
        }
    }

    private fun notifyError(exception: Throwable) {
        _mainCatState.value = NetworkResult.Error(exception.localizedMessage?:"Error")
    }
}