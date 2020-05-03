package com.example.covid_19tracker.Repository

import androidx.lifecycle.LiveData
import com.example.covid_19tracker.Database.CountyEntity
import com.example.covid_19tracker.Database.LocalCountryHistory

interface RepositoryContract {
    suspend fun refreshCountries()

    fun getCountryHistory(countryName: String): LiveData<LocalCountryHistory>
    suspend fun getCountryHistroyList(name :String):List<LocalCountryHistory>
    suspend fun getCountryData(countryName: String):LiveData<CountyEntity>?
    fun getAllSubscribedCountries(isSubscribed: Boolean):LiveData<List<CountyEntity>>
    fun updateSubscribedCountry(countryName: String, isSubscribed: Boolean)
    }