package com.example.covid_19tracker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.example.covid_19tracker.database.*
import com.example.covid_19tracker.domain.CountryModel
import com.example.covid_19tracker.domain.asCountryModel
import com.example.covid_19tracker.domain.asCountryModelList
import com.example.covid_19tracker.network.*
import com.example.covid_19tracker.utils.Covid_19Notification
import kotlinx.coroutines.*
import timber.log.Timber

class Repository(
    private val remoteDataSource: DiseaseAPI,
    private val localDataSource: CovidDao
) : RepositoryContract {
    override var countryList: LiveData<List<CountryModel>> =
        Transformations.map(localDataSource.getAllCountry()) {
            it.asCountryModelList()
        }


    override suspend fun refreshCountries(){
        withContext(Dispatchers.IO) {
            val countries = remoteDataSource.getCountriesData()
            val subscribedCountries= localDataSource.getAllCoutrySubscribed()
            localDataSource.insertCountry(* countries.asLocalCountryList().toTypedArray())
        }
    }

    private fun shouldNotify(countries: List<CountryData>,subscribed: List<CountryEntitySubscribed>) : Boolean{
        val isChanged = ArrayList<Boolean>(1)
        countries.forEach{ coutrydata ->
            subscribed.filter {
                return coutrydata.country.equals(it.country)
            }.any{
                isChanged[0] = (coutrydata.cases != it.totalCases)
                coutrydata.cases != it.totalCases
            }
        }
        return isChanged[0]
    }

    override suspend fun getCountryData(countryName: String): LiveData<CountryModel>? {
        return Transformations.map(localDataSource.getCountryByName(countryName)) {
            it.asCountryModel()
        }

    }

    override fun insertContrySubscribed(countryEntitySubscribed: CountryEntitySubscribed) {
        localDataSource.insertContrySubscribed(countryEntitySubscribed)
    }

    override fun deleteCountrySubscribed(countryEntitySubscribed: CountryEntitySubscribed) {
        localDataSource.deleteCountrySubscribed(countryEntitySubscribed)
    }

    override fun getAllCoutrySubscribed(): LiveData<List<CountryEntitySubscribed>> {
        return localDataSource.getAllCoutrySubscribed()
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


     fun updateSubscribedCountry(countryName: String, isSubscribed: Boolean) {
        localDataSource.updateSubscribedCountry(countryName,isSubscribed)
    }
}