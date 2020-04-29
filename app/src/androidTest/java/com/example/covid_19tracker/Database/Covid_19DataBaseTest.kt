package com.example.covid_19tracker.Database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class Covid_19DataBaseTest{
    private lateinit var covidDao: CovidDao
    private lateinit var db: Covid_19DataBase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, Covid_19DataBase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        covidDao = db.covidDao
    }
    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
    @Test
    @Throws(Exception::class)
    fun insertAndGetStat() {
        val country = Country("Menouf")
        covidDao.insertCountry(country)
        val insertedCountry = covidDao.getCountryByName("Menouf")
        assertNotNull(insertedCountry)
    }
}