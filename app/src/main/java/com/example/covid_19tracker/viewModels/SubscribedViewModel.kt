package com.example.covid_19tracker.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.covid_19tracker.database.LocalDataSource
import com.example.covid_19tracker.network.RemoteDataSource
import com.example.covid_19tracker.repository.Repository
import com.example.covid_19tracker.domain.CountryModel
import kotlinx.coroutines.launch

class SubscribedViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    private val covidRepo :Repository
    private var countriesLiveData : LiveData<List<CountryModel>>
    init {
        covidRepo = Repository(RemoteDataSource, LocalDataSource(application))
        countriesLiveData = MutableLiveData()
        viewModelScope.launch {
            countriesLiveData = covidRepo.countryList
        }
    }

    fun getFavouriteCountryList():LiveData<List<CountryModel>>{
        return countriesLiveData
    }
}
