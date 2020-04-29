package com.example.covid_19tracker.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.internal.PrepareOp
@Entity(tableName = "country")
data class Country(
    @PrimaryKey
    val name: String
)
@Entity(tableName = "stat")
data class Stat(
    @PrimaryKey
    val id: String="",
    val countryName: String="",
    val totalCases: String="",
    val newCases: String="",
    val activeCases: String="",
    val totalDeaths: String="",
    val newDeaths: String="",
    val totalRecovered: String="",
    val seriousCritical: String="",
    val recordDatePure: String=""
)
