package com.example.covid_19tracker.ViewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.covid_19tracker.CovidApplication
import com.example.covid_19tracker.Database.CountyEntity
import com.example.covid_19tracker.Database.LocalDataSource
import com.example.covid_19tracker.Network.RemoteDataSource
import com.example.covid_19tracker.Repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscribedViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    private val covidRepo :Repository
    private var countriesLiveData : LiveData<List<CountyEntity>>
    init {
        covidRepo = (application as CovidApplication).repository
        countriesLiveData = MutableLiveData()
        viewModelScope.launch {
            countriesLiveData = covidRepo.getAllSubscribedCountries(isSubscribed = true)
        }
    }

    fun getFavouriteCountryList():LiveData<List<CountyEntity>>{
        return countriesLiveData
    }

    fun deleteSubscribedCountry(countyEntity: CountyEntity){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                covidRepo.updateSubscribedCountry(countyEntity.country, false)
            }
        }
    }
}
