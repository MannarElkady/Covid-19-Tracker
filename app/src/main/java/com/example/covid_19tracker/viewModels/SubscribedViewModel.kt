package com.example.covid_19tracker.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.covid_19tracker.CovidApplication
import com.example.covid_19tracker.database.CountryEntitySubscribed
import com.example.covid_19tracker.database.CountyEntity

import com.example.covid_19tracker.database.LocalDataSource
import com.example.covid_19tracker.network.RemoteDataSource
import com.example.covid_19tracker.repository.Repository
import com.example.covid_19tracker.domain.CountryModel
import com.example.covid_19tracker.repository.RepositoryContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscribedViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    private val covidRepo :RepositoryContract
    private val countryList : LiveData<List<CountryModel>>
    private var countriesLiveData : LiveData<List<CountryEntitySubscribed>>
    init {
        covidRepo = (application.applicationContext as CovidApplication).repository
        countryList = covidRepo.countryList
        countriesLiveData = MutableLiveData()
        viewModelScope.launch {
            countriesLiveData = covidRepo.getAllCoutrySubscribedLiveData()
        }
    }

    fun getFavouriteCountryList():LiveData<List<CountryEntitySubscribed>>{
        return countriesLiveData
    }
    fun deleteSubscribedCountry(countryEntitySubscribed: CountryEntitySubscribed){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                covidRepo.deleteCountrySubscribed(countryEntitySubscribed)
            }
        }
    }

    fun getEquivalentCountryModel(countryEntitySubscribed: CountryEntitySubscribed): CountryModel{
        return countryList.value?.filter {
            it.country.equals(countryEntitySubscribed.country)
        }!!.first()
    }
}
