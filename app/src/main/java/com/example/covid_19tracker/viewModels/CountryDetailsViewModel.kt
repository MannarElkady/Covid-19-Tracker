package com.example.covid_19tracker.viewModels

import android.app.Application
import android.text.BoringLayout
import androidx.lifecycle.*
import com.example.covid_19tracker.CovidApplication
import com.example.covid_19tracker.database.CountryEntitySubscribed

import com.example.covid_19tracker.database.LocalCountryHistory
import com.example.covid_19tracker.database.LocalDataSource
import com.example.covid_19tracker.domain.CountryModel
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
    private var countryEntity: CountryModel? = null



    init {
        covidRepo = Repository(RemoteDataSource,LocalDataSource(application))
        countryHistoryLiveData = MutableLiveData()
     //   countryEntityLiveData = MutableLiveData()
        viewModelScope.launch {
            countryHistoryLiveData = covidRepo.getCountryHistory("Afghanistan")
          //  countryEntityLiveData = covidRepo.getCountryData("Afghanistan")
        }
    }

    public fun setCountryEntity(country: CountryModel){
        country.let {
            this.countryEntity = it
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
        viewModelScope.async {
            withContext(Dispatchers.IO) {
                countrySubscribed = covidRepo.getCountrySubscribed(countryName)
            }
        }
        return countrySubscribed
    }
//    fun updateCountrySubscribe(CountryModel: CountryModel, checkState: Boolean){
//        viewModelScope.launch {
//            withContext(Dispatchers.IO){
//                covidRepo.updateSubscribedCountry(CountryModel.country, checkState)
//            }
//        }
//    }
  

    fun deleteSubscribedCountry(countyEntity: CountryModel){
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
