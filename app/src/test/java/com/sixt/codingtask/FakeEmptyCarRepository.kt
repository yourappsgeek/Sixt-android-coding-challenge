package com.sixt.codingtask


import com.sixt.codingtask.data.Car
import com.sixt.codingtask.data.OperationResult
import com.sixt.codingtask.model.CarDataSource

class FakeEmptyCarRepository : CarDataSource {

    override suspend fun retrieveCars(): OperationResult<Car> {
        return OperationResult.Success(emptyList())
    }
}