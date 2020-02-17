package com.sixt.codingtask.data

/**
 * @CreatedBy Ali Ahsan
 *         Synavos Solutions
 *         Author Email: info.aliuetian@gmail.com
 *         Created on: 2020-02-17
 */
interface OperationCallback {
    fun onSuccess(obj: Any?)
    fun onError(obj: Any?)
}