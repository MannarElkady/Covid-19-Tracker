package com.example.covid_19tracker.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.covid_19tracker.CovidApplication
import com.example.covid_19tracker.database.LocalCountryHistory
import com.example.covid_19tracker.database.LocalDataSource
import com.example.covid_19tracker.domain.CountryModel
import com.example.covid_19tracker.network.RemoteDataSource
import com.example.covid_19tracker.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountryDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val covidRepo :Repository
    private var countryHistoryLiveData: LiveData<LocalCountryHistory>
    private var countryEntityLiveData : LiveData<CountryModel>?

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

    fun updateCountrySubscribe(CountryModel: CountryModel, checkState: Boolean){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                covidRepo.updateSubscribedCountry(CountryModel.country, checkState)
            }
        }
    }
    fun getCountryData() : LiveData<CountryModel>?{
        return countryEntityLiveData
    }
}
