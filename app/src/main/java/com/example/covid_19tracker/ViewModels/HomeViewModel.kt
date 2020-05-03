package com.example.covid_19tracker.ViewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.covid_19tracker.CovidApplication
import com.example.covid_19tracker.Database.CountyEntity
import com.example.covid_19tracker.Database.LocalCountryHistory
import com.example.covid_19tracker.Repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application)  {
    private val covidRepo : Repository
    private var countryEntityLiveData : LiveData<CountyEntity>?

    init {
        covidRepo = (application as CovidApplication).repository
        countryEntityLiveData = MutableLiveData()
        viewModelScope.launch {
            countryEntityLiveData = covidRepo.getCountryData("Egypt")
        }
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
