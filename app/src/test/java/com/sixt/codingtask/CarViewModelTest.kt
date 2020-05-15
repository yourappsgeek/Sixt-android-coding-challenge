package com.sixt.codingtask

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sixt.codingtask.data.Car
import com.sixt.codingtask.data.OperationResult
import com.sixt.codingtask.model.CarDataSource
import com.sixt.codingtask.viewmodel.CarViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.BDDMockito.`when`
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class CarViewModelTest {

    @Mock
    private lateinit var context: Application

    private lateinit var carViewModel: CarViewModel

    @Mock
    private lateinit var isViewLoadingObserver: Observer<Boolean>

    @Mock
    private lateinit var emptyListObserver: Observer<Boolean>

    @Mock
    private lateinit var carsObserver: Observer<List<Car>>

    @Mock
    private lateinit var repository: CarDataSource
    private lateinit var carList: List<Car>

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)

        `when`(context.applicationContext).thenReturn(context)

        carViewModel = CarViewModel(repository).apply {

            isLoading.observeForever(isViewLoadingObserver)
            isEmpty.observeForever(emptyListObserver)
            cars.observeForever(carsObserver)
        }

        Dispatchers.setMain(testDispatcher)

        mockData()

    }

    @ExperimentalCoroutinesApi
    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }


    @Test
    fun `retrieve cars with ViewModel and Repository returns empty data`() {


        runBlocking {

            `when`(repository.retrieveCars()).thenReturn(OperationResult.Success(emptyList()))

            carViewModel.loadCars()

            verify(repository).retrieveCars()

        }

        Assert.assertNotNull(carViewModel.isLoading.value)
        Assert.assertTrue(carViewModel.isEmpty.value == true)
        Assert.assertTrue(carViewModel.cars.value?.size == 0)

    }


    @Test
    fun `retrieve cars with ViewModel and Repository returns full data`() {

        runBlocking {

            `when`(repository.retrieveCars()).thenReturn(OperationResult.Success(carList))

            carViewModel.loadCars()

            verify(repository).retrieveCars()
        }

        Assert.assertNotNull(carViewModel.isLoading.value)
        Assert.assertTrue(carViewModel.cars.value?.size == 3)
    }


    @Test
    fun `retrieve cars with ViewModel and Repository returns an error`() {

        runBlocking {

            `when`(repository.retrieveCars()).thenReturn(
                OperationResult.Error(
                    Exception("Something went wrong. please try again later!")
                )
            )

            carViewModel.loadCars()

            verify(repository).retrieveCars()
        }

        Assert.assertNotNull(carViewModel.isLoading.value)
        Assert.assertNotNull(carViewModel.onError.value)
    }

    private fun mockData() {

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
            )
            ,
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
}