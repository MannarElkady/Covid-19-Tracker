package com.example.covid_19tracker.viewModels

import android.app.Application
import androidx.lifecycle.*

import com.example.covid_19tracker.database.LocalDataSource
import com.example.covid_19tracker.network.RemoteDataSource
import com.example.covid_19tracker.repository.Repository
import com.example.covid_19tracker.domain.CountryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscribedViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    private val covidRepo :Repository
    private var countriesLiveData : LiveData<List<CountryEntitySubscribed>>
    init {
        covidRepo = Repository(RemoteDataSource, LocalDataSource(application))
        countriesLiveData = MutableLiveData()
        viewModelScope.launch {
            countriesLiveData = covidRepo.getAllCoutrySubscribed()
        }
    }

    fun getFavouriteCountryList():LiveData<List<CountryEntitySubscribed>>{
        return countriesLiveData
    }
    fun deleteSubscribedCountry(countyEntity: CountryModel){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                covidRepo.deleteCountrySubscribed(CountryEntitySubscribed(countyEntity.country
                    ,countyEntity.cases,countyEntity.countryInfo.flag))
            }
        }
    }
}
