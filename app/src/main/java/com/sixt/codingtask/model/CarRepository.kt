package com.sixt.codingtask.model

import android.util.Log
import com.sixt.codingtask.data.ApiClient
import com.sixt.codingtask.data.Car
import com.sixt.codingtask.data.OperationCallback
import com.sixt.codingtask.data.ResponseCode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @CreatedBy Ali Ahsan
 *
 *         Author Email: info.aliuetian@gmail.com
 *         Created on: 2020-02-17
 */

const val TAG = "CONSOLE"

class CarRepository : CarDataSource {

    private var call: Call<List<Car>>? = null

    override fun retrieveCars(callback: OperationCallback) {
        call = ApiClient.build()?.cars()
        call?.enqueue(object : Callback<List<Car>> {
            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                response.let {
                    if (response.isSuccessful && (response.code() == ResponseCode.SUCCESS.code)) {
                        Log.v(TAG, "data $it")
                        callback.onSuccess(it.body())
                    } else {
                        callback.onError(response.message())
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.cancel()
    }
}