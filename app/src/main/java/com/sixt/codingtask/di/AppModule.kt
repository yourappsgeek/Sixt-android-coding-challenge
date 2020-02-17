package com.sixt.codingtask.di

import com.sixt.codingtask.model.CarDataSource
import com.sixt.codingtask.model.CarRepository
import com.sixt.codingtask.viewmodel.CarViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @CreatedBy Ali Ahsan
 *         Synavos Solutions
 *         Author Email: info.aliuetian@gmail.com
 *         Created on: 2020-02-17
 */
val appModule = module {
    single<CarDataSource> { CarRepository() }

    viewModel { CarViewModel(get()) }
}