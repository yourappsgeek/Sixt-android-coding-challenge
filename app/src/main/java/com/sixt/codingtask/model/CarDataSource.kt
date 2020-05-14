package com.sixt.codingtask.model

import com.sixt.codingtask.data.OperationCallback

/**
 * @CreatedBy Ali Ahsan
 *
 *         Author Email: info.aliuetian@gmail.com
 *         Created on: 2020-02-17
 */
interface CarDataSource {

    fun retrieveCars(callback: OperationCallback)
    fun cancel()
}