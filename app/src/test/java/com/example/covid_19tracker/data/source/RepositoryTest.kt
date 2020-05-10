package com.example.covid_19tracker.data.source

import com.example.covid_19tracker.network.*
import com.example.covid_19tracker.repository.Repository
import com.example.covid_19tracker.repository.RepositoryContract
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
@ExperimentalCoroutinesApi
class RepositoryTest {

    private lateinit var remoteDataSource: FakeRemoteSource
    private lateinit var localDataSource: FakeLocalSource

    // Class under test
    private lateinit var repository: RepositoryContract

    @Before
    fun createRepository() {
        remoteDataSource =
            FakeRemoteSource(createMockCountry().toMutableList(), createMockData().toMutableList())
        localDataSource = FakeLocalSource(
            createMockCountry().asLocalCountryList().toMutableList(),
            createMockData().asLocalCountryHistoryList().toMutableList()
        )
        // Get a reference to the class under test
        repository = Repository(
            remoteDataSource = remoteDataSource, localDataSource = localDataSource
        )
    }
    @Test
    fun getTasks_requestsAllTasksFromRemoteDataSource()= runBlockingTest{
        // When tasks are requested from the tasks repository
        val historyList = repository.getCountryHistroyList("USA")
        historyList.forEach{print(it)}
        // Then tasks are loaded from the remote data source
        assertThat(historyList.size, IsEqual(remoteDataSource.history.size+1))
    }


}
fun createMockData(): List<CountryHistory> {
    return listOf(
        CountryHistory(
            country = "Afghanistan", timeline = History(
                cases = mapOf<String, Long>(
                    "3/31/20" to 174,
                    "4/1/20" to 237,
                    "4/2/20" to 273,
                    "4/3/20" to 281,
                    "4/4/20" to 299,
                    "4/5/20" to 349,
                    "4/6/20" to 367,
                    "4/7/20" to 423,
                    "4/8/20" to 444,
                    "4/9/20" to 484,
                    "4/10/20" to 521,
                    "4/11/20" to 555,
                    "4/12/20" to 607,
                    "4/13/20" to 665,
                    "4/14/20" to 714,
                    "4/15/20" to 784
                ), deaths = mapOf<String, Long>(
                    "3/31/20" to 174,
                    "4/1/20" to 237,
                    "4/2/20" to 273,
                    "4/3/20" to 281,
                    "4/4/20" to 299,
                    "4/5/20" to 349,
                    "4/6/20" to 367,
                    "4/7/20" to 423,
                    "4/8/20" to 444
                ), recovered = mapOf<String, Long>(
                    "3/31/20" to 174,
                    "4/1/20" to 237,
                    "4/2/20" to 273,
                    "4/3/20" to 281,
                    "4/4/20" to 299,
                    "4/5/20" to 349,
                    "4/6/20" to 367,
                    "4/7/20" to 423,
                    "4/8/20" to 444
                )
            ), province = listOf("one", "two", "three")
        ), CountryHistory(
            country = "USA", timeline = History(
                cases = mapOf<String, Long>(
                    "3/31/20" to 174,
                    "4/1/20" to 237,
                    "4/2/20" to 273,
                    "4/3/20" to 281,
                    "4/4/20" to 299,
                    "4/5/20" to 349,
                    "4/6/20" to 367,
                    "4/7/20" to 423,
                    "4/8/20" to 444,
                    "4/9/20" to 484,
                    "4/10/20" to 521,
                    "4/11/20" to 555,
                    "4/12/20" to 607,
                    "4/13/20" to 665,
                    "4/14/20" to 714,
                    "4/15/20" to 784
                ), deaths = mapOf<String, Long>(
                    "3/31/20" to 174,
                    "4/1/20" to 237,
                    "4/2/20" to 273,
                    "4/3/20" to 281,
                    "4/4/20" to 299,
                    "4/5/20" to 349,
                    "4/6/20" to 367,
                    "4/7/20" to 423,
                    "4/8/20" to 444
                ), recovered = mapOf<String, Long>(
                    "3/31/20" to 174,
                    "4/1/20" to 237,
                    "4/2/20" to 273,
                    "4/3/20" to 281,
                    "4/4/20" to 299,
                    "4/5/20" to 349,
                    "4/6/20" to 367,
                    "4/7/20" to 423,
                    "4/8/20" to 444
                )
            ), province = listOf("one", "two", "three")
        )
    )
}

fun createMockCountry(): List<CountryData> {
    return listOf(
        CountryData(
            country = "USA",
            countryInfo = CountryInfo(
                _id = 4,
                iso2 = "AF",
                iso3 = "AFG",
                lat = 33.0,
                long = 65.0,
                flag = "https://corona.lmao.ninja/assets/img/flags/af.png"
            )
            , cases = 2171,
            todayCases = 232,
            deaths = 64,
            todayDeaths = 4,
            recovered = 260,
            active = 1847,
            critical = 7,
            casesPerOneMillion = 56,
            deathsPerOneMillion = 2,
            updated = 1588287677285

        ), CountryData(
            country = "Afghanistan",
            countryInfo = CountryInfo(
                _id = 5,
                iso2 = "AF",
                iso3 = "AFG",
                lat = 33.0,
                long = 65.0,
                flag = "https://corona.lmao.ninja/assets/img/flags/af.png"
            )
            , cases = 8000,
            todayCases = 232,
            deaths = 64,
            todayDeaths = 4,
            recovered = 260,
            active = 1847,
            critical = 7,
            casesPerOneMillion = 56,
            deathsPerOneMillion = 2,
            updated = 1588287677285

        )
    )

}
