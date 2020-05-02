package com.example.covid_19tracker.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CovidDao {
    @Insert(onConflict =OnConflictStrategy.REPLACE )
    fun insertCountry(vararg country: CountyEntity)
    @Insert(onConflict =OnConflictStrategy.REPLACE )
    fun insertCountryHistory(countryHistory: LocalCountryHistory)
    @Query("Select * FROM country")
    fun getAllCountry():LiveData<List<CountyEntity>>
    @Query("SELECT * FROM country_history WHERE country=:countryName")
    fun geCountryHistory(countryName:String):LiveData<LocalCountryHistory>
    @Query("SELECT * FROM country WHERE isSubscribed=:isSubscribed")
    fun getAllSubscribedCountries(isSubscribed: Boolean):LiveData<List<CountyEntity>>
    @Query("UPDATE country SET isSubscribed = :isSubscribed WHERE country = :countryName")
    fun updateSubscribedCountry(countryName: String, isSubscribed: Boolean)
    // just for testing
    @Query("SELECT * FROM country WHERE country=:countryName")
    fun getCountryByName(countryName:String):LiveData<CountyEntity>?
    @Query("SELECT * FROM country_history")
    fun getAllHistory():List<LocalCountryHistory>
}