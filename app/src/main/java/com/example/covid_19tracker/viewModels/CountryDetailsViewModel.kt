package com.example.covid_19tracker.viewModels

import android.app.Application
import android.text.BoringLayout
import androidx.lifecycle.*
import com.example.covid_19tracker.CovidApplication
import com.example.covid_19tracker.database.CountryEntitySubscribed
import com.example.covid_19tracker.database.CountyEntity
import com.example.covid_19tracker.database.LocalCountryHistory
import com.example.covid_19tracker.database.LocalDataSource
import com.example.covid_19tracker.network.RemoteDataSource
import com.example.covid_19tracker.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountryDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val covidRepo :Repository
    private var countryHistoryLiveData: LiveData<LocalCountryHistory>
   // private var countryEntityLiveData : LiveData<CountyEntity>?
    private var countryEntity: CountyEntity? = null


    init {
        covidRepo = Repository(RemoteDataSource,LocalDataSource(application))
        countryHistoryLiveData = MutableLiveData()
     //   countryEntityLiveData = MutableLiveData()
        viewModelScope.launch {
            countryHistoryLiveData = covidRepo.getCountryHistory("Afghanistan")
          //  countryEntityLiveData = covidRepo.getCountryData("Afghanistan")
        }
    }

    public fun setCountryEntity(countyEntity: CountyEntity){
        countryEntity?.let {
            this.countryEntity = countryEntity
        }
    }

    fun getCountryHistory() : LiveData<LocalCountryHistory>{
        return countryHistoryLiveData
    }

    fun addCountrySubscribed(countyEntity: CountyEntity){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                covidRepo.insertContrySubscribed(CountryEntitySubscribed(countyEntity.country,
                    countyEntity.cases,countyEntity.countryInfo.flag))
            }
        }
    }

    fun isCountrySubscribed(countryName: String): LiveData<CountryEntitySubscribed>{
        var countrySubscribed: LiveData<CountryEntitySubscribed> = MutableLiveData<CountryEntitySubscribed>()
        viewModelScope.async {
            withContext(Dispatchers.IO){
               countrySubscribed = covidRepo.getCountrySubscribed(countryName)
            }
        }
        return countrySubscribed
    }

    fun deleteSubscribedCountry(countyEntity: CountyEntity){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                covidRepo.deleteCountrySubscribed(CountryEntitySubscribed(countyEntity.country
                    ,countyEntity.cases,countyEntity.countryInfo.flag))
            }
        }
    }

//    fun getCountryData() : LiveData<CountyEntity>?{
//        return countryEntityLiveData
//    }
}
