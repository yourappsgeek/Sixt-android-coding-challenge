package com.sixt.codingtask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sixt.codingtask.data.Car
import com.sixt.codingtask.data.OperationResult
import com.sixt.codingtask.model.CarDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @CreatedBy Ali Ahsan
 *         Synavos Solutions
 *         Author Email: info.aliuetian@gmail.com
 *         Created on: 2020-02-17
 */
class CarViewModel(private val repository: CarDataSource) : ViewModel() {

    private val _cars = MutableLiveData<List<Car>>().apply { value = emptyList() }
    val cars: LiveData<List<Car>> = _cars

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _onError = MutableLiveData<Any>()
    val onError: LiveData<Any> = _onError

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    init {

        loadCars()
    }

    fun loadCars() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            val result: OperationResult<Car> = withContext(Dispatchers.IO) {
                repository.retrieveCars()
            }
            _isLoading.postValue(false)
            when (result) {
                is OperationResult.Success -> {
                    if (result.data.isNullOrEmpty()) {
                        _isEmpty.postValue(true)
                    } else {
                        _cars.value = result.data
                    }
                }
                is OperationResult.Error -> {
                    _onError.postValue(result.exception)

                }
            }
        }
    }

}