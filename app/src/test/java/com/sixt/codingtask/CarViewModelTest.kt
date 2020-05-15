package com.sixt.codingtask

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sixt.codingtask.data.Car
import com.sixt.codingtask.data.OperationCallback
import com.sixt.codingtask.model.CarDataSource
import com.sixt.codingtask.viewmodel.CarViewModel
import org.junit.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.*

/**
 * @CreatedBy Ali Ahsan
 *
 *         Author Email: info.aliuetian@gmail.com
 *         Created on: 2020-02-17
 */
class CarViewModelTest : KoinTest {

    @Mock
    private lateinit var repository: CarDataSource

    @Mock
    private lateinit var context: Application

    @Captor
    private lateinit var operationCallbackCaptor: ArgumentCaptor<OperationCallback>

    private val carViewModel: CarViewModel by inject()

    @Mock
    private lateinit var isViewLoadingObserver: Observer<Boolean>

    @Mock
    private lateinit var emptyListObserver: Observer<Boolean>

    @Mock
    private lateinit var carsObserver: Observer<List<Car>>

    private lateinit var carEmptyList: List<Car>
    private lateinit var carList: List<Car>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        startKoin {
            printLogger()
            loadKoinModules(module {
                single { repository }
                viewModel {
                    CarViewModel(get())
                }
            })
        }

        //setup
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(context.applicationContext).thenReturn(context)

        carViewModel.apply {

            isLoading.observeForever(isViewLoadingObserver)
            isEmpty.observeForever(emptyListObserver)
            cars.observeForever(carsObserver)
        }

        mockData()
    }

    @Test
    fun `empty cars list with Repository and ViewModel`() {

        Mockito.verify(repository, Mockito.times(1)).retrieveCars(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(carEmptyList)

        Assert.assertNotNull(carViewModel.isLoading.value)
        Assert.assertTrue(carViewModel.isEmpty.value == true)
        Assert.assertTrue(carViewModel.cars.value?.size == 0)
    }

    @Test
    fun `successful cars list with Repository and ViewModel`() {

        Mockito.verify(repository, Mockito.times(1)).retrieveCars(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(carList)

        Assert.assertNotNull(carViewModel.isLoading.value)
        Assert.assertTrue(carViewModel.cars.value?.size == 3)
    }

    @Test
    fun `failure cars list with Repository and ViewModel`() {

        Mockito.verify(repository, Mockito.times(1)).retrieveCars(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onError("Something went wrong. please try again later!")

        Assert.assertNotNull(carViewModel.isLoading.value)
        Assert.assertNotNull(carViewModel.onError.value)
    }

    private fun mockData() {
        carEmptyList = emptyList()
        carList = listOf(

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
                ),

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
                ),

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

    @After
    fun after() {
        stopKoin()
    }
}