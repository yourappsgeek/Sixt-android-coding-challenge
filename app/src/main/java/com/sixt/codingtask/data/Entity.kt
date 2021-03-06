package com.sixt.codingtask.data

/**
 * @CreatedBy Ali Ahsan
 *
 *         Author Email: info.aliuetian@gmail.com
 *         Created on: 2020-02-17
 */

data class Car(
    val id: String,
    val name: String,
    val make: String,
    val color: String,
    val series: String,
    val latitude: Double,
    val longitude: Double,
    val innerCleanliness: String,
    val carImageUrl: String
)