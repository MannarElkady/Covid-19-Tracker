package com.example.covid_19tracker.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.covid_19tracker.Database.*
import com.example.covid_19tracker.Network.*
import kotlinx.coroutines.*

class Repository(
    private val remoteDataSource: DiseaseAPI,
    private val localDataSource: CovidDao,
    private val ioDispatcher: CoroutineDispatcher=Dispatchers.IO
) : RepositoryContract {
    val countries: LiveData<List<CountyEntity>>
        get() = localDataSource.getAllCountry()

    override suspend fun refreshCountries() {
        val countries = remoteDataSource.getCountriesData()
        localDataSource.insertCountry(* countries.asLocalCountryList().toTypedArray())
    }
    override suspend fun getCountryData(countryName: String):LiveData<CountyEntity>?{
        return localDataSource.getCountryByName(countryName)
    }

    override fun getAllSubscribedCountries(isSubscribed: Boolean): LiveData<List<CountyEntity>> {
        return localDataSource.getAllSubscribedCountries(isSubscribed)
    }

    override fun updateSubscribedCountry(countryName: String, isSubscribed: Boolean) {
        localDataSource.updateSubscribedCountry(countryName,isSubscribed)
    }


    override fun getCountryHistory(countryName: String):LiveData<LocalCountryHistory> {
         runBlocking {
             withContext(ioDispatcher) {
                 val countryHistory = remoteDataSource.getCountryHistory(countryName)
                 localDataSource.insertCountryHistory(countryHistory.asLocalCountryHistory())
             }
         }
         return localDataSource.geCountryHistory(countryName)
    }
    //For test Repository not form production
   override suspend fun getCountryHistroyList(name :String):List<LocalCountryHistory>{
        val countryHistory = remoteDataSource.getCountryHistory(name)
        localDataSource.insertCountryHistory(countryHistory.asLocalCountryHistory())
        return localDataSource.getAllHistory()
    }
}