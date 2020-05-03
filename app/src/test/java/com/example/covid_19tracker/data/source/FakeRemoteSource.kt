package com.example.covid_19tracker.data.source

import com.example.covid_19tracker.network.*

class FakeRemoteSource(var countries:MutableList<CountryData>,var history: MutableList<CountryHistory>): DiseaseAPI {
    override suspend fun getGeneralInfo(): GeneralInfo {
        TODO("Not yet implemented")
    }

    override suspend fun getCountriesData(): List<CountryData> {
        return countries
    }

    override suspend fun getCountryData(countryName: String): CountryData {
        return countries.find { it.country == countryName }!!
    }

    override suspend fun getCountryHistory(countryName: String): CountryHistory {
        return history.find { it.country==countryName }!!
    }
}