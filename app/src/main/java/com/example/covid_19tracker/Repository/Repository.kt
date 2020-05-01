package com.example.covid_19tracker.Repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.covid_19tracker.Database.*
import com.example.covid_19tracker.Network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val remoteDataSource: DiseaseAPI,
    private val localDataSource: CovidDao
) : RepositoryContract {

    val countries: LiveData<List<CountyEntity>>
        get() = localDataSource.getAllCountry()

    override suspend fun refreshCountries() {
        val countries = remoteDataSource.getCountriesData()
        localDataSource.insertCountry(* countries.asLocalCountryList().toTypedArray())
        countries.forEach{
            val countryHistory = remoteDataSource.getCountryHistory(it.country)
            localDataSource.insertCountryHistory(countryHistory.asLocalCountryHistory())
        }
    }
    override suspend fun getCountryData(countryName: String):CountyEntity?{
        return localDataSource.getCountryByName(countryName)
    }
    override suspend fun getCountryHistory(countryName: String):LiveData<LocalCountryHistory> {
         return localDataSource.geCountryHistory(countryName)
    }
    //For test Repository not form production
   override suspend fun getCountryHistroyList(name :String):List<LocalCountryHistory>{
        val countryHistory = remoteDataSource.getCountryHistory(name)
        localDataSource.insertCountryHistory(countryHistory.asLocalCountryHistory())
        return localDataSource.getAllHistory()
    }
}