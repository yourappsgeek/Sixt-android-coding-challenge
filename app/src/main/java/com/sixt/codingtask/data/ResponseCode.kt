package com.sixt.codingtask.data

/**
 * @CreatedBy Ali Ahsan
 *
 *         Author Email: info.aliuetian@gmail.com
 *         Created on: 2020-02-16
 */
enum class ResponseCode(val code: Int) {

    // ===== POSITIVE ===== //
    SUCCESS(200),
    CREATED(201),
    NOCONTENT(204),

    // ===== NEGATIVE ===== //
    UNKNOWN(4232),
    ERROR(400),
    CONNECTION_TIMEOUT(-10),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    FORGED_RESPONSE(-899),

    // ===== RECEIVED FROM SERVER ===== //
    GENERAL_ERROR(-1),
    SESSION_TIMEOUT(-2),
    INTERNAL_SERVER_ERROR(500);
    // ============================== //
}