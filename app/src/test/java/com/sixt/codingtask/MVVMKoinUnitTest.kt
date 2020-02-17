package com.sixt.codingtask

import android.app.Application
import android.content.Context
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
 *         Synavos Solutions
 *         Author Email: info.aliuetian@gmail.com
 *         Created on: 2020-02-17
 */
class MVVMKoinUnitTest : KoinTest {

    @Mock
    private lateinit var repository: CarDataSource
    @Mock
    private lateinit var context: Application
    @Captor
    private lateinit var operationCallbackCaptor: ArgumentCaptor<OperationCallback>

    private val carViewModel: CarViewModel by inject()

    private lateinit var isViewLoadingObserver: Observer<Boolean>
    private lateinit var onMessageErrorObserver: Observer<Any>
    private lateinit var emptyListObserver: Observer<Boolean>
    private lateinit var onRenderMuseumsObserver: Observer<List<Car>>

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
        Mockito.`when`<Context>(context.applicationContext).thenReturn(context)

        mockData()
        setupObservers()
    }

    @Test
    fun `empty museums list with Repository and ViewModel`() {
        with(carViewModel) {
            loadCars()
            isLoading.observeForever(isViewLoadingObserver)
            isEmpty.observeForever(emptyListObserver)
            cars.observeForever(onRenderMuseumsObserver)
        }

        Mockito.verify(repository, Mockito.times(1)).retrieveCars(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(carEmptyList)
        Assert.assertNotNull(carViewModel.isLoading.value)
        Assert.assertTrue(carViewModel.isEmpty.value == true)
        Assert.assertTrue(carViewModel.cars.value?.size == 0)
    }

    @Test
    fun `museum list successful with Repository and ViewModel`() {
        with(carViewModel) {
            loadCars()
            isLoading.observeForever(isViewLoadingObserver)
            cars.observeForever(onRenderMuseumsObserver)
        }

        Mockito.verify(repository, Mockito.times(1)).retrieveCars(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(carList)

        Assert.assertNotNull(carViewModel.isLoading.value)
        Assert.assertTrue(carViewModel.cars.value?.size == 3)
    }

    @Test
    fun `museum list failure with Repository and ViewModel`() {
        with(carViewModel) {
            loadCars()
            isLoading.observeForever(isViewLoadingObserver)
            onError.observeForever(onMessageErrorObserver)
        }
        Mockito.verify(repository, Mockito.times(1)).retrieveCars(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onError("Ocurrió un error")
        Assert.assertNotNull(carViewModel.isLoading.value)
        Assert.assertNotNull(carViewModel.onError.value)
    }

    private fun setupObservers() {
        isViewLoadingObserver = Mockito.mock(Observer::class.java) as Observer<Boolean>
        onMessageErrorObserver = Mockito.mock(Observer::class.java) as Observer<Any>
        emptyListObserver = Mockito.mock(Observer::class.java) as Observer<Boolean>
        onRenderMuseumsObserver = Mockito.mock(Observer::class.java) as Observer<List<Car>>
    }

    private fun mockData() {
        carEmptyList = emptyList()
        val mockList: MutableList<Car> = mutableListOf()
        mockList.add(
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
            )
        )
        mockList.add(
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
            )
        )
        mockList.add(
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

        carList = mockList.toList()
    }

    @After
    fun after() {
        stopKoin()
    }
}