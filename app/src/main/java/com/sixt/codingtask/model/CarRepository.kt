package com.sixt.codingtask.model

import android.util.Log
import com.sixt.codingtask.data.ApiClient
import com.sixt.codingtask.data.Car
import com.sixt.codingtask.data.OperationResult
import com.sixt.codingtask.data.ResponseCode

/**
 * @CreatedBy Ali Ahsan
 *         Synavos Solutions
 *         Author Email: info.aliuetian@gmail.com
 *         Created on: 2020-02-17
 */

const val TAG = "CONSOLE"

class CarRepository : CarDataSource {

    override suspend fun retrieveCars(): OperationResult<Car> {

        try {
            val response = ApiClient.build()?.cars()

            response?.let {
                return if (it.isSuccessful && it.body() != null && (it.code() == ResponseCode.SUCCESS.code)) {

                    Log.v(TAG, "data $it")
                    OperationResult.Success(it.body())
                } else {
                    OperationResult.Error(Exception(it.message()))
                }
            } ?: run {
                return OperationResult.Error(Exception("Something went wrong. please try again later!"))
            }
        } catch (e: Exception) {
            return OperationResult.Error(e)
        }
    }
}