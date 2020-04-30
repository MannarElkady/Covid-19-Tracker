package com.example.covid_19tracker.Network

class RemoteDataSource : DiseaseAPI {
    private val network by lazy { NetworkService.INSTANCE }
    override suspend fun getGeneralInfo(): GeneralInfo {
        return network.getGeneralInfo()
    }

    override suspend fun getCountriesData(): List<CountryData> {
        return network.getCountriesData()
    }

    override suspend fun getCountryData(countryName: String): CountryData {
        return network.getCountryData(countryName)
    }

    override suspend fun getCountryHistory(countryName: String): CountryHistory {
        return network.getCountryHistory(countryName)
    }
}