package com.example.covid_19tracker.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface CovidDao {
    @Insert(onConflict =OnConflictStrategy.REPLACE )
    fun insertCountry(country: Country)
    @Insert(onConflict =OnConflictStrategy.REPLACE )
    fun insertStat(stat: Stat)
    @Query("Select * FROM country")
    fun getAllCountry():LiveData<List<Country>>
    @Query("SELECT * FROM stat WHERE countryName=:countryName")
    fun getStatByCountry(countryName:String):LiveData<List<Stat>>
    // just for testing
    @Query("SELECT * FROM country WHERE name=:countryName")
    fun getCountryByName(countryName:String):Country
}