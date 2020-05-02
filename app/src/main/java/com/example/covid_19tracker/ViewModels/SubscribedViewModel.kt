package com.example.covid_19tracker.ViewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.covid_19tracker.Database.CountyEntity
import com.example.covid_19tracker.Database.LocalDataSource
import com.example.covid_19tracker.Network.RemoteDataSource
import com.example.covid_19tracker.Repository.Repository
import kotlinx.coroutines.launch

class SubscribedViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    private val covidRepo :Repository
    private var countriesLiveData : LiveData<List<CountyEntity>>
    init {
        covidRepo = Repository(RemoteDataSource(), LocalDataSource(application))
        countriesLiveData = MutableLiveData()
        viewModelScope.launch {
            countriesLiveData = covidRepo.countries
        }
    }

    fun getFavouriteCountryList():LiveData<List<CountyEntity>>{
        return countriesLiveData
    }
}
