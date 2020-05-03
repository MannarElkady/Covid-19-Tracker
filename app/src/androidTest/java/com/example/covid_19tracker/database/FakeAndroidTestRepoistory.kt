package com.example.covid_19tracker.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.covid_19tracker.domain.CountryModel
import com.example.covid_19tracker.domain.asCountryModelList
import com.example.covid_19tracker.network.CountryData
import com.example.covid_19tracker.network.CountryInfo
import com.example.covid_19tracker.repository.RepositoryContract
import kotlinx.coroutines.runBlocking
import java.util.LinkedHashMap

class FakeAndroidTestRepoistory:RepositoryContract {
    private var _countryList = MutableLiveData<List<CountryModel>>()
    var countryServiceData: LinkedHashMap<String, CountyEntity> = LinkedHashMap()
    var historyServiceData: LinkedHashMap<String, LocalCountryHistory> = LinkedHashMap()

    override val countryList: LiveData<List<CountryModel>>
        get() = _countryList

    override suspend fun refreshCountries() {
        _countryList.postValue( countryServiceData.values.toList().asCountryModelList())
    }

    override fun getCountryHistory(countryName: String): LiveData<LocalCountryHistory> {
        return MutableLiveData(historyServiceData[countryName])
    }

    override suspend fun getCountryHistroyList(name: String): List<LocalCountryHistory> {
        return historyServiceData.values.toList()
    }

    override suspend fun getCountryData(countryName: String): LiveData<CountyEntity>? {
        return MutableLiveData(countryServiceData[countryName])
    }

    override fun orderList(order: String) {
        _countryList.value?.sortedBy { countryModel -> countryModel.cases }
    }

    fun addTasks(vararg countryList: CountyEntity) {
        for (country in countryList) {
            countryServiceData[country.country] = country
        }
        runBlocking { refreshCountries() }
    }
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
