package com.example.covid_19tracker.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.covid_19tracker.database.CountyEntity
import com.example.covid_19tracker.database.LocalCountryHistory
import com.example.covid_19tracker.database.LocalDataSource
import com.example.covid_19tracker.network.RemoteDataSource
import com.example.covid_19tracker.repository.Repository
import kotlinx.coroutines.launch

class CountryDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val covidRepo :Repository
    private var countryHistoryLiveData: LiveData<LocalCountryHistory>
    private var countryEntityLiveData : LiveData<CountyEntity>?

    init {
        covidRepo = Repository(RemoteDataSource,LocalDataSource(application))
        countryHistoryLiveData = MutableLiveData()
        countryEntityLiveData = MutableLiveData()
        viewModelScope.launch {
            countryHistoryLiveData = covidRepo.getCountryHistory("Afghanistan")
            countryEntityLiveData = covidRepo.getCountryData("Afghanistan")
        }
    }

    fun getCountryHistory() : LiveData<LocalCountryHistory>{
        return countryHistoryLiveData
    }

    fun getCountryData() : LiveData<CountyEntity>?{
        return countryEntityLiveData
    }
}
