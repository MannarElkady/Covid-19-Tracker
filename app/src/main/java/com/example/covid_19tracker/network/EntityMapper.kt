package com.example.covid_19tracker.network

import com.example.covid_19tracker.database.CountyEntity
import com.example.covid_19tracker.database.LocalCountryHistory
import com.example.covid_19tracker.database.LocalCountryInfo
import com.example.covid_19tracker.database.LocalHistory

fun CountryData.asLocalCountryEntity(): CountyEntity {
    return CountyEntity(
        this.country,
        this.countryInfo.asLocalCountryInfo(),
        this.cases,
        this.todayCases,
        this.deaths,
        this.todayDeaths,
        this.recovered,
        this.active,
        this.critical,
        this.casesPerOneMillion,
        this.deathsPerOneMillion,
        this.updated
    )
}

fun CountryInfo.asLocalCountryInfo(): LocalCountryInfo {
    return LocalCountryInfo(
        iso2 = iso2 ?: "",
        iso3 = iso3 ?: "",
        flag = flag ?: "",
        lat = lat,
        long = long
    )
}

fun CountryHistory.asLocalCountryHistory(): LocalCountryHistory {
    return LocalCountryHistory(this.country, this.provinces, this.timeline.asLocalHistory())
}

fun History.asLocalHistory(): LocalHistory {
    return LocalHistory(cases = cases, deaths = deaths, recovered = recovered)
}

fun List<CountryData>.asLocalCountryList(): List<CountyEntity> {
    return map { it.asLocalCountryEntity() }
}

fun List<CountryHistory>.asLocalCountryHistoryList(): List<LocalCountryHistory> {
    return map { it.asLocalCountryHistory() }
}

fun List<GeneralInfo>.asCountryEntityList(): List<CountyEntity> {
    return map { it.asCountryEntity() }
}

fun GeneralInfo.asCountryEntity(): CountyEntity {
    return CountyEntity(
        country = "total_world",
        todayCases = todayCases,
        todayDeaths = todayDeaths,
        updated = updated,
        deathsPerOneMillion = deathsPerOneMillion,
        casesPerOneMillion = casesPerOneMillion,
        critical = critical,
        cases = cases,
        active = active,
        recovered = recovered,
        deaths = deaths,
        countryInfo = LocalCountryInfo()
    )
}