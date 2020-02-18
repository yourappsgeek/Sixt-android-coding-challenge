package com.sixt.codingtask

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sixt.codingtask.data.Car
import com.sixt.codingtask.data.OperationResult
import com.sixt.codingtask.viewmodel.CarViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations


class UnitTestsUsingCoroutines {

    @Mock
    private lateinit var context: Application


    private lateinit var viewModel: CarViewModel

    private lateinit var isViewLoadingObserver: Observer<Boolean>
    private lateinit var onMessageErrorObserver: Observer<Any>
    private lateinit var emptyListObserver: Observer<Boolean>
    private lateinit var onRenderMuseumsObserver: Observer<List<Car>>

    private val fakeMuseumRepository = FakeCarRepository()
    private val fakeEmptyMuseumRepository = FakeEmptyCarRepository()
    private val fakeErrorMuseumRepository = FakeErrorCarRepository()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`<Context>(context.applicationContext).thenReturn(context)
        Dispatchers.setMain(testDispatcher)

        setupObservers()
    }

    @ExperimentalCoroutinesApi
    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `retrieve cars with ViewModel and Repository returns empty data`() {
        viewModel = CarViewModel(fakeEmptyMuseumRepository)

        with(viewModel) {
            loadCars()
            isLoading.observeForever(isViewLoadingObserver)
            isEmpty.observeForever(emptyListObserver)
            cars.observeForever(onRenderMuseumsObserver)
        }

        runBlockingTest {
            val response = fakeEmptyMuseumRepository.retrieveCars()
            Assert.assertTrue(response is OperationResult.Success)
            Assert.assertNotNull(viewModel.isLoading.value)
            Assert.assertTrue(viewModel.isEmpty.value == true)
            Assert.assertTrue(viewModel.cars.value?.size == 0)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `retrieve cars with ViewModel and Repository returns full data`() {
        viewModel = CarViewModel(fakeMuseumRepository)

        with(viewModel) {
            loadCars()
            isLoading.observeForever(isViewLoadingObserver)
            cars.observeForever(onRenderMuseumsObserver)
        }

        runBlockingTest {
            val response = fakeMuseumRepository.retrieveCars()
            Assert.assertTrue(response is OperationResult.Success)
            Assert.assertNotNull(viewModel.isLoading.value)
            Assert.assertTrue(viewModel.cars.value?.size == 3)
        }
    }

    @Test
    fun `retrieve cars with ViewModel and Repository returns an error`() {
        viewModel = CarViewModel(fakeErrorMuseumRepository)
        with(viewModel) {
            loadCars()
            isLoading.observeForever(isViewLoadingObserver)
            isEmpty.observeForever(emptyListObserver)
            cars.observeForever(onRenderMuseumsObserver)
        }

        runBlockingTest {
            val response = fakeErrorMuseumRepository.retrieveCars()
            Assert.assertTrue(response is OperationResult.Error)
            Assert.assertNotNull(viewModel.isLoading.value)
            Assert.assertNotNull(viewModel.onError.value)
        }
    }

    private fun setupObservers() {
        isViewLoadingObserver = mock(Observer::class.java) as Observer<Boolean>
        onMessageErrorObserver = mock(Observer::class.java) as Observer<Any>
        emptyListObserver = mock(Observer::class.java) as Observer<Boolean>
        onRenderMuseumsObserver = mock(Observer::class.java) as Observer<List<Car>>
    }
}