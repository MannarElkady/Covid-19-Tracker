package com.example.covid_19tracker.data.source

import androidx.lifecycle.LiveData
import com.example.covid_19tracker.database.CountyEntity
import com.example.covid_19tracker.database.CovidDao
import com.example.covid_19tracker.database.LocalCountryHistory

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

    override fun getCountryByCases(): LiveData<List<CountyEntity>> {
        TODO("Not yet implemented")
    }

    override fun getCountryByDeath(): List<CountyEntity> {
        TODO("Not yet implemented")
    }

    override fun getCountryByRecovered(): List<CountyEntity> {
        TODO("Not yet implemented")
    }

    fun getOrderedCountry(filter: String): LiveData<List<CountyEntity>> {
        TODO("Not yet implemented")

    }
     fun getAllSubscribedCountries(isSubscribed: Boolean): LiveData<List<CountyEntity>> {
        TODO("Not yet implemented")
    }

     fun updateSubscribedCountry(countryName: String, isSubscribed: Boolean) {
        TODO("Not yet implemented")
    }


     override fun getCountryByName(countryName: String): LiveData<CountyEntity>? {
        TODO("Not yet implemented")
    }

     override fun getAllHistory(): List<LocalCountryHistory> {
        history?.also { return it }
        return  emptyList()
    }
}