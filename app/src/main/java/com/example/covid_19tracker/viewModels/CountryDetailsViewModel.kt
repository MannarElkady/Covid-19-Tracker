package com.example.covid_19tracker.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.covid_19tracker.CovidApplication
import com.example.covid_19tracker.database.CountryEntitySubscribed

import com.example.covid_19tracker.database.LocalCountryHistory
import com.example.covid_19tracker.domain.CountryModel
import com.example.covid_19tracker.repository.RepositoryContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountryDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val covidRepo :RepositoryContract
    private var countryHistoryLiveData: LiveData<LocalCountryHistory>
    private var countryEntity: CountryModel? = null

    init {
        covidRepo = (application.applicationContext as CovidApplication).repository
        countryHistoryLiveData = MutableLiveData()
    }

    public fun setCountryEntity(country: CountryModel){
        country?.let {
            this.countryEntity = it
            viewModelScope.launch {
                countryHistoryLiveData = covidRepo.getCountryHistory(country.country)
            }
        }

    }

    fun getCountryHistory() : LiveData<LocalCountryHistory>{
        return countryHistoryLiveData
    }

    fun addCountrySubscribed(countyEntity: CountryModel){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                covidRepo.insertContrySubscribed(CountryEntitySubscribed(countyEntity.country,
                    countyEntity.cases,countyEntity.countryInfo.flag))
            }
        }
    }

    fun isCountrySubscribed(countryName: String): LiveData<CountryEntitySubscribed> {
        var countrySubscribed: LiveData<CountryEntitySubscribed> =
            MutableLiveData<CountryEntitySubscribed>()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                countrySubscribed = covidRepo.getCountrySubscribed(countryName)
            }
        }
        return countrySubscribed
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
