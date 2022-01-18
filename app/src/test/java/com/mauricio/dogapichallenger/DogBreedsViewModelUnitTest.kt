package com.mauricio.dogapichallenger

import android.app.Application
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mauricio.dogapichallenger.BuildConfig.VERSION_CODE
import com.mauricio.dogapichallenger.breeds.repository.BreedsRepository
import com.mauricio.dogapichallenger.breeds.viewmodel.DogBreedsViewModel
import com.mauricio.dogapichallenger.utils.Constant.ORDER_BY_DESCENDING
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
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
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import javax.inject.Inject

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
@ExperimentalCoroutinesApi
class DogBreedsViewModelUnitTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Mock
    lateinit var viewModel: DogBreedsViewModel
    @Inject
    lateinit var repository: BreedsRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        hiltRule.inject()
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = DogBreedsViewModel(repository)
    }

    @ExperimentalCoroutinesApi
    @After
    fun setDown() {
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