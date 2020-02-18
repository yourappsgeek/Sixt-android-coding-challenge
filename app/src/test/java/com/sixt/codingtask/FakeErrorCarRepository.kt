package com.sixt.codingtask

import com.sixt.codingtask.data.Car
import com.sixt.codingtask.data.OperationResult
import com.sixt.codingtask.model.CarDataSource

class FakeErrorCarRepository : CarDataSource {

    private val mockException = Exception("Something went wrong")

    override suspend fun retrieveCars(): OperationResult<Car> {
        return OperationResult.Error(mockException)
    }
}