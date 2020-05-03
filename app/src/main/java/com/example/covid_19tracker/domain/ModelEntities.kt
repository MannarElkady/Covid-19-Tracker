package com.example.covid_19tracker.domain

import com.example.covid_19tracker.database.CountyEntity
import com.example.covid_19tracker.database.LocalCountryInfo
/*
*   Model class that represents in UI to user
*
* */
data class CountryModel(
    val country: String,
    val countryInfo: LocalCountryInfo
    , val cases: Long
    , val deaths: Long
    , val recovered: Long
)

fun List<CountyEntity>.asCountryModelList(): List<CountryModel> {
    return map {
        CountryModel(
            country = it.country,
            countryInfo = it.countryInfo,
            cases = it.cases,
            deaths = it.deaths,
            recovered = it.recovered
        )
    }
}