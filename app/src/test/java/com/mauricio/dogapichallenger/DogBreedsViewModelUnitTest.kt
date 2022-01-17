package com.mauricio.dogapichallenger

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mauricio.dogapichallenger.breeds.viewmodel.DogBreedsViewModel
import com.mauricio.dogapichallenger.utils.Constant.ORDER_BY_DESCENDING
import junit.framework.TestCase
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class DogBreedsViewModelUnitTest {

    @Mock
    lateinit var viewModel: DogBreedsViewModel
    @Mock
    lateinit var mockContext: Application

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockitoAnnotations.openMocks(this)
        viewModel = DogBreedsViewModel(mockContext)
    }

    @ExperimentalCoroutinesApi
    @After
    fun down() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }

    @Test
    fun `Check Total Dog Breed`() = runBlocking {
        viewModel.getBreeds()
        delay(2000)
        assertNotNull(viewModel.breeds.value)
        assertEquals(172, viewModel.breeds.value?.size)
    }

    @Test
    fun `Check Dog Breed Order By Descending`() = runBlocking {
        viewModel.getBreeds()
        delay(3000)
        assertNotNull(viewModel.breeds.value)
        val breeds = viewModel.breeds.value
        val breed = breeds?.get(0)
        viewModel.orderByBreeds(ORDER_BY_DESCENDING)
        val breedsAfterOrder = viewModel.breeds.value
        val breedAfterOrder = breedsAfterOrder?.get(0)
        assertNotEquals(breed?.name, breedAfterOrder?.name)
    }

    @Test
    fun `Check Search Dog Breed By Akita`() = runBlocking {
        viewModel.getBreeds()
        delay(2000)
        assertNotNull(viewModel.breeds.value)
        val breeds = viewModel.breeds.value
        val breed = breeds?.firstOrNull { it.name == "Akita" }
        assertNotNull(breed)
        viewModel.searchBreedById(breed!!.id)
        delay(2000)
        val breedsAfterSearch = viewModel.breedsBySearch.value
        assertNotNull(breedsAfterSearch)
        assertEquals(12, breedsAfterSearch?.size)
    }
}