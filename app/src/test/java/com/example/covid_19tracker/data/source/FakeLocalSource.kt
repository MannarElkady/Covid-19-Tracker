package com.example.covid_19tracker.data.source

import androidx.lifecycle.LiveData
import com.example.covid_19tracker.Database.CountyEntity
import com.example.covid_19tracker.Database.CovidDao
import com.example.covid_19tracker.Database.LocalCountryHistory

class FakeLocalSource(
    var countries: MutableList<CountyEntity>?,
    var history: MutableList<LocalCountryHistory>?
) : CovidDao {
    override fun insertCountry(vararg country: CountyEntity) {
        countries?.addAll(country)
    }

    override fun insertCountryHistory(countryHistory: LocalCountryHistory) {
        history?.add(countryHistory)
    }

    override fun getAllCountry(): LiveData<List<CountyEntity>> {
        TODO("Not yet implemented")

    }

    override fun geCountryHistory(countryName: String): LiveData<LocalCountryHistory> {
        TODO("Not yet implemented")
    }

    override fun getAllSubscribedCountries(isSubscribed: Boolean): LiveData<List<CountyEntity>> {
        TODO("Not yet implemented")
    }

    override fun updateSubscribedCountry(countryName: String, isSubscribed: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getCountryByName(countryName: String): CountyEntity? {

        var countyEntity = countries?.first { it.country == countryName }
        countyEntity.also { return it }
    }

    override fun getAllHistory(): List<LocalCountryHistory> {
        history?.also { return it }
        return  emptyList()
    }
}