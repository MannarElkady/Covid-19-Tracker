package com.example.covid_19tracker.Network

import com.example.covid_19tracker.Database.CountyEntity
import com.example.covid_19tracker.Database.LocalCountryHistory
import com.example.covid_19tracker.Database.LocalCountryInfo
import com.example.covid_19tracker.Database.LocalHistory

public fun CountyData.asLocalCountryEntity() : CountyEntity {
    return CountyEntity(this.country,this.countryInfo.asLocalCountryInfo(),this.cases,this.todayCases,this.deaths,
        this.todayDeaths,this.recovered,this.active,this.critical,this.casesPerOneMillion,
        this.deathsPerOneMillion,this.updated)
}

public fun CountryInfo.asLocalCountryInfo() : LocalCountryInfo {
    return LocalCountryInfo(this._id,this.iso2,this.iso3,this.flag,this.lat,this.long)
}

public fun CountryHistory.asLocalCountryHistory() : LocalCountryHistory {
    return LocalCountryHistory(this.country,this.provinces,this.timeline.asLocalHistory())
}

public fun History.asLocalHistory() : LocalHistory {
    return LocalHistory(this.cases,this.deaths,this.recovered)
}

