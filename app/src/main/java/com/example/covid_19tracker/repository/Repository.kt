package com.example.covid_19tracker.repository

import androidx.lifecycle.*
import com.example.covid_19tracker.database.*
import com.example.covid_19tracker.domain.CountryModel
import com.example.covid_19tracker.domain.asCountryModel
import com.example.covid_19tracker.domain.asCountryModelList
import com.example.covid_19tracker.network.*
import kotlinx.coroutines.*

class Repository(
    private val remoteDataSource: DiseaseAPI,
    private val localDataSource: CovidDao
) : RepositoryContract {
    override var countryList: LiveData<List<CountryModel>> =
        Transformations.map(localDataSource.getAllCountry()) {
            it.asCountryModelList()
        }
override val totalWorld: LiveData<CountryModel> =
    Transformations.map(localDataSource.getTotalWorld()) {
        it.asCountryModel()
    }
    override suspend fun refreshCountries(){
        withContext(Dispatchers.IO) {
            val countries = remoteDataSource.getCountriesData()
            val totalWorld = remoteDataSource.getGeneralInfo()
            localDataSource.insertCountry(* countries.asLocalCountryList().toTypedArray())
            localDataSource.insertCountry(totalWorld.asCountryEntity())
        }
    }

    private fun shouldNotify(countries: List<CountryData>,subscribed: CountryEntitySubscribed) : Boolean{
        val newCases = countries.filter {
            it.country.equals(subscribed.country)
        }.first().cases
        val notify = subscribed.totalCases != newCases
        localDataSource.updateCountrySubscriped(CountryEntitySubscribed(subscribed.country,newCases,subscribed.countryThumb))
        return notify
    }

    override suspend fun getCountryData(countryName: String): LiveData<CountryModel>? {
        return Transformations.map(localDataSource.getCountryByName(countryName)) {
            it.asCountryModel()
        }
    }

    override suspend fun notifyCountries(): List<String> {
        val listOfCoutriesToNotify = arrayListOf<String>()
        withContext(Dispatchers.IO) {
            val countries = remoteDataSource.getCountriesData()
            val subscribedList = getAllCoutrySubscribed()
            subscribedList.forEach {
                if(shouldNotify(countries,it)) {
                    listOfCoutriesToNotify.add(it.country)
                }
            }
            localDataSource.insertCountry(* countries.asLocalCountryList().toTypedArray())
        }
        return listOfCoutriesToNotify
    }

    override fun updateCountrySubscriped(countryEntitySubscribed: CountryEntitySubscribed) {
        return localDataSource.updateCountrySubscriped(countryEntitySubscribed)
    }

    override fun insertContrySubscribed(countryEntitySubscribed: CountryEntitySubscribed) {
        localDataSource.insertContrySubscribed(countryEntitySubscribed)
    }

    override fun deleteCountrySubscribed(countryEntitySubscribed: CountryEntitySubscribed) {
        localDataSource.deleteCountrySubscribed(countryEntitySubscribed)
    }

    override suspend fun getAllCoutrySubscribed(): List<CountryEntitySubscribed> {
        var listOfCountry : List<CountryEntitySubscribed> = listOf()
        runBlocking {
            withContext(Dispatchers.IO){
                listOfCountry = localDataSource.getAllCoutrySubscribed()
            }
        }
        return listOfCountry
    }

    override fun getAllCoutrySubscribedLiveData(): LiveData<List<CountryEntitySubscribed>> {
        return localDataSource.getAllCoutrySubscribedLiveData()
    }

    override fun getCountrySubscribed(countryName: String): LiveData<CountryEntitySubscribed> {
        return localDataSource.getCountrySubscribed(countryName)
    }


    override fun getCountryHistory(countryName: String): LiveData<LocalCountryHistory> {
        runBlocking {
            withContext(Dispatchers.IO) {
                val countryHistory = remoteDataSource.getCountryHistory(countryName)
                localDataSource.insertCountryHistory(countryHistory.asLocalCountryHistory())
            }
        }

        return localDataSource.geCountryHistory(countryName)
    }

    //For test Repository not form production
    override suspend fun getCountryHistroyList(name: String): List<LocalCountryHistory> {
        val countryHistory = remoteDataSource.getCountryHistory(name)
        localDataSource.insertCountryHistory(countryHistory.asLocalCountryHistory())
        return localDataSource.getAllHistory()
    }

}