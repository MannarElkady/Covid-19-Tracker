package com.example.covid_19tracker.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CovidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountry(vararg country: CountyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountryHistory(countryHistory: LocalCountryHistory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContrySubscribed(countryEntitySubscribed: CountryEntitySubscribed)

    @Delete
    fun deleteCountrySubscribed(countryEntitySubscribed :CountryEntitySubscribed)

    @Query("Select * From countrySubscribed")
    fun getAllCoutrySubscribed(): List<CountryEntitySubscribed>

    @Query("Select * From countrySubscribed")
    fun getAllCoutrySubscribedLiveData(): LiveData<List<CountryEntitySubscribed>>

    @Query("Select * FROM country")
    fun getAllCountry(): LiveData<List<CountyEntity>>

    @Query("SELECT * FROM countrySubscribed WHERE country=:countryName")
    fun getCountrySubscribed(countryName: String):LiveData<CountryEntitySubscribed>

    @Query("SELECT * FROM country_history WHERE country=:countryName")
    fun geCountryHistory(countryName: String): LiveData<LocalCountryHistory>

    @Query("SELECT * FROM country ORDER BY cases DESC ")
    fun getCountryByCases(): LiveData<List<CountyEntity>>

    @Query("SELECT * FROM country ORDER BY deaths DESC ")
    fun getCountryByDeath(): List<CountyEntity>

    @Query("SELECT * FROM country ORDER BY recovered DESC ")
    fun getCountryByRecovered(): List<CountyEntity>

    // just for testing
    @Query("SELECT * FROM country WHERE country=:countryName")
    fun getCountryByName(countryName: String): LiveData<CountyEntity>

    @Query("SELECT * FROM country_history")
    fun getAllHistory(): List<LocalCountryHistory>

}