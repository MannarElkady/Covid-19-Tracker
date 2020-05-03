package com.example.covid_19tracker.ui.fragments

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.covid_19tracker.R
import com.example.covid_19tracker.ServiceLocator
import com.example.covid_19tracker.database.FakeAndroidTestRepoistory
import com.example.covid_19tracker.database.createMockCountry
import com.example.covid_19tracker.network.asLocalCountryList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class HomeFragmentTest {
    private lateinit var repository: FakeAndroidTestRepoistory

    @Before
    fun initRepository() {
        repository = FakeAndroidTestRepoistory()
        ServiceLocator.repository = repository
    }

    @After
    fun cleanupDb() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    @Test
    fun clickTask_navigateToDetailFragmentOne() = runBlockingTest{
        // When add New Country
        repository.addTasks(*createMockCountry().asLocalCountryList().toTypedArray())

        // GIVEN - On the home screen
        val scenario = launchFragmentInContainer<HomeFragment>(Bundle(), R.style.AppTheme)
        val navController = Mockito.mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        // WHEN - Click on the first list item
        Espresso.onView(withId(R.id.country_list))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(ViewMatchers.withId(R.id.show_more)),
                    ViewActions.click()
                )
            )


        // THEN - Verify that we navigate to the first detail screen
        Mockito.verify(navController).navigate(
            HomeFragmentDirections.actionHomeFragmentToCountryDetails()
        )
    }

    @Test
    fun onOrderChipDisplayed() {
// GIVEN - On the home screen
        val scenario = launchFragmentInContainer<HomeFragment>(Bundle(), R.style.AppTheme)
        val navController = Mockito.mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
    }
}