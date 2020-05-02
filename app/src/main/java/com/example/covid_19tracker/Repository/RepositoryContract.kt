package com.example.covid_19tracker.Repository

import androidx.lifecycle.LiveData
import com.example.covid_19tracker.Database.LocalCountryHistory

interface RepositoryContract {
    suspend fun refreshCountries()

     fun getCountryHistory(countryName: String): LiveData<LocalCountryHistory>
    suspend fun getCountryHistroyList(name :String):List<LocalCountryHistory>
    suspend fun getCountryData(countryName: String):LiveData<CountyEntity>?
    }