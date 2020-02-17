package com.sixt.codingtask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sixt.codingtask.data.Car
import com.sixt.codingtask.data.OperationCallback
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
        viewModelScope.launch {

            withContext(Dispatchers.IO)
            {
                loadCars()
            }

        }
    }

    fun loadCars() {
        _isLoading.postValue(true)
        repository.retrieveCars(object : OperationCallback {
            override fun onError(obj: Any?) {
                _isLoading.postValue(false)
                _onError.postValue(obj)
            }

            override fun onSuccess(obj: Any?) {
                _isLoading.postValue(false)

                if (obj != null && obj is List<*>) {
                    if (obj.isEmpty()) {
                        _isEmpty.postValue(true)
                    } else _cars.value = obj as List<Car>
                }
            }
        })
    }

}