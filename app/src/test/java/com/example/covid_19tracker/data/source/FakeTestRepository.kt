package com.example.covid_19tracker.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.covid_19tracker.database.CountyEntity
import com.example.covid_19tracker.database.LocalCountryHistory
import com.example.covid_19tracker.domain.CountryModel
import com.example.covid_19tracker.domain.asCountryModelList
import com.example.covid_19tracker.repository.RepositoryContract
import kotlinx.coroutines.runBlocking
import java.util.LinkedHashMap

class FakeTestRepository : RepositoryContract {
    private var _countryList = MutableLiveData<List<CountryModel>>()
    var countryServiceData: LinkedHashMap<String, CountyEntity> = LinkedHashMap()
    var historyServiceData: LinkedHashMap<String, LocalCountryHistory> = LinkedHashMap()

    override val countryList: LiveData<List<CountryModel>>
        get() =_countryList

    override suspend fun refreshCountries() {
        _countryList.value = countryServiceData.values.toList().asCountryModelList()
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