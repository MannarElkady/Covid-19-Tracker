package com.example.covid_19tracker.Database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.covid_19tracker.Network.moshi
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import org.json.JSONObject

@Entity(tableName = "country")
data class CountyEntity(
    @PrimaryKey
    val country: String,
    @Embedded
    val countryInfo: LocalCountryInfo
    , val cases: Long
    , val todayCases: Long
    , val deaths: Long
    , val todayDeaths: Long
    , val recovered: Long
    , val active: Long
    , val critical: Long
    , val casesPerOneMillion: Long
    , val deathsPerOneMillion: Long
    , val updated: Long
)

@Entity(tableName = "country_info")
data class LocalCountryInfo(
    @PrimaryKey
    var id: Int? = -1
    , var iso2: String? = ""
    , var iso3: String? = ""
    , var flag: String? = ""
    , var lat: Double = 0.0
    , var long: Double = 0.0
)


@Entity(tableName = "country_history")
data class LocalCountryHistory(
    @PrimaryKey
    val country: String
    , val provinces: List<String>,
    @Embedded
    val timeline: LocalHistory
)

@Entity(tableName = "history")
data class LocalHistory(
    val cases: Map<String, Long>
    , val deaths: Map<String, Long>
    , val recovered: Map<String, Long>,
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1
)

class MapConverter {
    @TypeConverter
    fun fromStringMap(data: String): Map<String, Long>? {
        val type = Types.newParameterizedType(Map::class.java, String::class.java, Long::class.java)
        val adapter: JsonAdapter<Map<String, Long>> = moshi.adapter(type)
        var map = adapter.fromJson(data)
        return map
    }

    @TypeConverter
    fun fromMapString(map: Map<String, Long>): String {
        return JSONObject(map).toString()
    }
}

class ListConverter{

    @TypeConverter
    fun restoreList(listOfString: String): List<String>? {
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter: JsonAdapter<List<String>> = moshi.adapter(type)
        return adapter.fromJson(listOfString)
    }

    @TypeConverter
    fun saveList(listOfString: List<String>): String {
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter: JsonAdapter<List<String>> = moshi.adapter(type)
        return adapter.toJson(listOfString)
    }
}