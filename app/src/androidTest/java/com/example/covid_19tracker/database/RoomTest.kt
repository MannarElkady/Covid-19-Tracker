package com.example.covid_19tracker.database

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
class RoomTest {
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
        // Given insert mock country history data
        covidDao.insertCountryHistory(createMockData()[0])
        covidDao.insertCountryHistory(createMockData()[1])
        // when get all data from database
        val historyList = covidDao.getAllHistory()
        assertEquals(historyList.size, 2)
        assertEquals(174L,historyList[0].timeline.cases["3/31/20"])
    }

    fun createMockData(): List<LocalCountryHistory> {
        return listOf(
            LocalCountryHistory(
                country = "Afghanistan", timeline = LocalHistory(
                    cases = mapOf<String,Long>(
                        "3/31/20" to 174,
                        "4/1/20" to 237,
                        "4/2/20" to 273,
                        "4/3/20" to 281,
                        "4/4/20" to 299,
                        "4/5/20" to 349,
                        "4/6/20" to 367,
                        "4/7/20" to 423,
                        "4/8/20" to 444,
                        "4/9/20" to 484,
                        "4/10/20" to 521,
                        "4/11/20" to 555,
                        "4/12/20" to 607,
                        "4/13/20" to 665,
                        "4/14/20" to 714,
                        "4/15/20" to 784
                    ), deaths = mapOf<String,Long>(
                        "3/31/20" to 174,
                        "4/1/20" to 237,
                        "4/2/20" to 273,
                        "4/3/20" to 281,
                        "4/4/20" to 299,
                        "4/5/20" to 349,
                        "4/6/20" to 367,
                        "4/7/20" to 423,
                        "4/8/20" to 444
                    ), recovered = mapOf<String,Long>(
                        "3/31/20" to 174,
                        "4/1/20" to 237,
                        "4/2/20" to 273,
                        "4/3/20" to 281,
                        "4/4/20" to 299,
                        "4/5/20" to 349,
                        "4/6/20" to 367,
                        "4/7/20" to 423,
                        "4/8/20" to 444
                    )
                ), provinces = listOf("one", "two", "three")
            ), LocalCountryHistory(
                country = "USA", timeline = LocalHistory(
                    cases = mapOf<String,Long>(
                        "3/31/20" to 174,
                        "4/1/20" to 237,
                        "4/2/20" to 273,
                        "4/3/20" to 281,
                        "4/4/20" to 299,
                        "4/5/20" to 349,
                        "4/6/20" to 367,
                        "4/7/20" to 423,
                        "4/8/20" to 444,
                        "4/9/20" to 484,
                        "4/10/20" to 521,
                        "4/11/20" to 555,
                        "4/12/20" to 607,
                        "4/13/20" to 665,
                        "4/14/20" to 714,
                        "4/15/20" to 784
                    ), deaths = mapOf<String,Long>(
                        "3/31/20" to 174,
                        "4/1/20" to 237,
                        "4/2/20" to 273,
                        "4/3/20" to 281,
                        "4/4/20" to 299,
                        "4/5/20" to 349,
                        "4/6/20" to 367,
                        "4/7/20" to 423,
                        "4/8/20" to 444
                    ), recovered = mapOf<String,Long>(
                        "3/31/20" to 174,
                        "4/1/20" to 237,
                        "4/2/20" to 273,
                        "4/3/20" to 281,
                        "4/4/20" to 299,
                        "4/5/20" to 349,
                        "4/6/20" to 367,
                        "4/7/20" to 423,
                        "4/8/20" to 444
                    )
                ), provinces = listOf("one", "two", "three")
            )
        )
    }
}
