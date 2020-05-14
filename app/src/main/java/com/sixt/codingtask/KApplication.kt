package com.sixt.codingtask

import android.app.Application
import com.sixt.codingtask.di.appModule
import org.koin.core.context.startKoin

/**
 * @CreatedBy Ali Ahsan
 *
 *         Author Email: info.aliuetian@gmail.com
 *         Created on: 2020-02-17
 */
class KApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}