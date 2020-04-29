package com.example.covid_19tracker.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.covid_19tracker.Database.CountyEntity
import com.example.covid_19tracker.Database.CovidDao
import com.example.covid_19tracker.Database.Covid_19DataBase
import com.example.covid_19tracker.Database.LocalHistory
import com.example.covid_19tracker.Network.DiseaseAPI
import com.example.covid_19tracker.Network.NetworkService

class Repository(application: Application) {
    private var dao: CovidDao
    private var network: DiseaseAPI

    //TODO create live Data
    init {
        val dataBase = Covid_19DataBase.getInstance(application)
        dao = dataBase.covidDao
        network = NetworkService.INSTANCE
    }

    fun refreshCountries() {
        //TODO implement Refresh data
    }

    fun getCountryHistory(countryName: String) {
        // TODO implement get country history
    }

    fun insertCountry(country: CountyEntity) {

    }

    fun insertHistory(localHistory: LocalHistory) {

    }

}