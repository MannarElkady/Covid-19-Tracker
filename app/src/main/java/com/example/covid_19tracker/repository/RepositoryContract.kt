package com.example.covid_19tracker.repository

import androidx.lifecycle.LiveData
import com.example.covid_19tracker.database.CountryEntitySubscribed
import com.example.covid_19tracker.database.CountyEntity
import com.example.covid_19tracker.database.LocalCountryHistory
import com.example.covid_19tracker.domain.CountryModel

interface RepositoryContract {
    val countryList: LiveData<List<CountryModel>>
    suspend fun refreshCountries()
    fun getCountryHistory(countryName: String): LiveData<LocalCountryHistory>
    suspend fun getCountryHistroyList(name: String): List<LocalCountryHistory>
    suspend fun getCountryData(countryName: String): LiveData<CountyEntity>?
    fun insertContrySubscribed(countryEntitySubscribed: CountryEntitySubscribed)
    fun deleteCountrySubscribed(countryEntitySubscribed: CountryEntitySubscribed)
    fun getAllCoutrySubscribed(): LiveData<List<CountryEntitySubscribed>>
    fun getCountrySubscribed(countryName: String):LiveData<CountryEntitySubscribed>
    fun orderList()
}