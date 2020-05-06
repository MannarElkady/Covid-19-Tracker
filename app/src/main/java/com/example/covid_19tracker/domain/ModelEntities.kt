package com.example.covid_19tracker.domain

import android.os.Parcelable
import com.example.covid_19tracker.database.CountyEntity
import com.example.covid_19tracker.database.LocalCountryInfo
import kotlinx.android.parcel.Parcelize

/*
*   Model class that represents in UI to user
*
* */
@Parcelize
data class CountryModel(
    val country: String,
    val countryInfo: LocalCountryInfo
    , val cases: Long
    , val deaths: Long
    , val recovered: Long,
    val todayDeaths: Long
    , val active: Long
    , val critical: Long
    , val casesPerOneMillion: Long
    , val deathsPerOneMillion: Long
    , val updated: Long,
     val todayCases: Long

): Parcelable

fun List<CountyEntity>.asCountryModelList(): List<CountryModel> {
    return map {
        CountryModel(
            country = it.country,
            countryInfo = it.countryInfo,
            cases = it.cases,
            deaths = it.deaths,
            recovered = it.recovered,
            todayDeaths = it.todayDeaths,
            active = it.active,
            critical = it.critical,
            casesPerOneMillion = it.casesPerOneMillion,
            deathsPerOneMillion = it.deathsPerOneMillion,
            updated = it.updated,
            todayCases = it.todayCases
        )
    }
}

fun CountyEntity.asCountryModel(): CountryModel {
    return CountryModel(
        country = country,
        countryInfo = countryInfo,
        cases = cases,
        deaths = deaths,
        recovered = recovered,
        todayDeaths = todayDeaths,
        active = active,
        critical = critical,
        casesPerOneMillion = casesPerOneMillion,
        deathsPerOneMillion = deathsPerOneMillion,
        updated = updated,
        todayCases = todayCases
    )
}
