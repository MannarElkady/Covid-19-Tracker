package com.example.covid_19tracker.Database

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import java.lang.Appendable

class LocalDataSource(context: Context) : CovidDao {
    private val dao: CovidDao
    init {
        val dataBase = Covid_19DataBase.getInstance(context.applicationContext)
        dao = dataBase.covidDao
    }

    override fun insertCountry(vararg country: CountyEntity) {
        dao.insertCountry(* country)
    }

    override fun insertCountryHistory(countryHistory: LocalCountryHistory) {
        dao.insertCountryHistory(countryHistory)
    }

    override fun getAllCountry(): LiveData<List<CountyEntity>> {
        return dao.getAllCountry()
    }

    override fun geCountryHistory(countryName: String): LiveData<LocalCountryHistory> {
        return dao.geCountryHistory(countryName)
    }

    override fun getCountryByName(countryName: String): CountyEntity? {
        return dao.getCountryByName(countryName)
    }

    override fun getAllHistory(): List<LocalCountryHistory> {
        return dao.getAllHistory()
    }
}