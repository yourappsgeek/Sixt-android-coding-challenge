package com.sixt.codingtask

import com.sixt.codingtask.data.Car
import com.sixt.codingtask.data.OperationResult
import com.sixt.codingtask.model.CarDataSource

class FakeCarRepository : CarDataSource {

    private val mockList: MutableList<Car> = mutableListOf()

    init {
        mockData()
    }

    private fun mockData() {
        mockList.add(
            Car(
                "1",
                "Vicki+",
                "BMW",
                "schwarz",
                "MINI",
                48.193826,
                11.563875,
                "VERY_CLEAN",
                ""
            )
        )
        mockList.add(
            Car(
                "2",
                "Casimir",
                "BMW",
                "absolute_black_metal",
                "MINI",
                48.193826,
                11.563875,
                "VERY_CLEAN",
                ""
            )
        )
        mockList.add(
            Car(
                "3",
                "Quentin",
                "BMW",
                "midnight_black",
                "MINI",
                48.125121,
                11.556484,
                "REGULAR",
                ""
            )
        )
    }

    override suspend fun retrieveCars(): OperationResult<Car> {
        return OperationResult.Success(mockList)
    }
}