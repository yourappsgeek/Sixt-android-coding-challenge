package com.sixt.codingtask.model

import com.sixt.codingtask.data.Car
import com.sixt.codingtask.data.OperationResult

/**
 * @CreatedBy Ali Ahsan
 *         Synavos Solutions
 *         Author Email: info.aliuetian@gmail.com
 *         Created on: 2020-02-17
 */
interface CarDataSource {

    suspend fun retrieveCars(): OperationResult<Car>
}