package com.example.covid_19tracker.ViewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.covid_19tracker.CovidApplication
import com.example.covid_19tracker.Database.CountyEntity
import com.example.covid_19tracker.Database.LocalCountryHistory
import com.example.covid_19tracker.Database.LocalDataSource
import com.example.covid_19tracker.Network.RemoteDataSource
import com.example.covid_19tracker.Repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountryDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val covidRepo :Repository
    private var countryHistoryLiveData: LiveData<LocalCountryHistory>
    private var countryEntityLiveData : LiveData<CountyEntity>?

    init {
        covidRepo = (application as CovidApplication).repository
        countryHistoryLiveData = MutableLiveData()
        countryEntityLiveData = MutableLiveData()
        viewModelScope.launch {
            countryHistoryLiveData = covidRepo.getCountryHistory("Egypt")
            countryEntityLiveData = covidRepo.getCountryData("Egypt")
        }
    }

    fun getCountryHistory() : LiveData<LocalCountryHistory>{
        return countryHistoryLiveData
    }

    fun updateCountrySubscribe(countyEntity: CountyEntity, checkState: Boolean){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                covidRepo.updateSubscribedCountry(countyEntity.country, checkState)
            }
        }
    }
    fun getCountryData() : LiveData<CountyEntity>?{
        return countryEntityLiveData
    }
}
