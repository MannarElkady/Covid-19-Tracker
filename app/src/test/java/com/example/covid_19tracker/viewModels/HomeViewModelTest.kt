package com.example.covid_19tracker.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.covid_19tracker.domain.asCountryModelList
import com.example.covid_19tracker.network.asLocalCountryList
import com.example.covid_19tracker.data.source.FakeTestRepository
import com.example.covid_19tracker.data.source.createMockCountry
import com.example.covid_19tracker.domain.CountryModel
import com.example.covid_19tracker.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    // Subject under test
    private lateinit var homeViewModel: HomeViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var repository: FakeTestRepository

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Before
    fun setupViewModel() {
        // We initialise the tasks to 3, with one active and two completed
        repository = FakeTestRepository()
        repository.addTasks(*createMockCountry().asLocalCountryList().toTypedArray())
        Dispatchers.setMain(mainThreadSurrogate)
        runBlockingTest {
            homeViewModel = HomeViewModel(repository)
        }
    }

    @Test
    fun getCountryList() {
        // When add New Country
        repository.addTasks(*createMockCountry().asLocalCountryList().toTypedArray())
        // Then the new task event is triggered
        val value = homeViewModel.countryList.getOrAwaitValue()

        assertThat(value, `is`(repository.countryServiceData.values.toList().asCountryModelList()))
    }

    @Test
    fun getNavigateToCountryDetails() {
        // When adding a new Country
        homeViewModel.onCountryClicked("USA")

        // Then the new task event is triggered
        val value = homeViewModel.navigateToCountryDetails.getOrAwaitValue()

        assertThat(value, not(nullValue()))

    }


    @Test
    fun onFilterChanged() {
        // when filter Data
        homeViewModel.onFilterChanged("cases", true)
        val orderdList = homeViewModel.countryList.getOrAwaitValue()
        //That countryList is ordered by cases
        assertArrayEquals(orderdList.toTypedArray(), createOrderCountry().toTypedArray())


    }

    private fun createOrderCountry(): List<CountryModel> {
        return createMockCountry().asLocalCountryList().asCountryModelList()
            .sortedBy { countyEntity -> countyEntity.cases }
    }
}