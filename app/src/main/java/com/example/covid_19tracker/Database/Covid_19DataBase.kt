package com.example.covid_19tracker.database

import android.content.Context
import androidx.room.*

/**
 * A database that stores Covid-19 information.
 * And a global method to get access to the database.
 *
 * This pattern is pretty much the same for any database,
 * so you can reuse it.
 */
@Database(entities = [CountyEntity::class,LocalCountryHistory::class,LocalCountryInfo::class,LocalHistory::class,CountryEntitySubscribed::class], version = 1, exportSchema = false)
@TypeConverters(MapConverter::class,ListConverter::class)
abstract class Covid_19DataBase : RoomDatabase() {

    /**
     * Connects the database to the DAO.
     */
    abstract val covidDao: CovidDao

    companion object {
        /**
         * INSTANCE will keep a reference to any database returned via getInstance.
         *
         * This will help us avoid repeatedly initializing the database, which is expensive.
         *
         *  The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. It means that changes made by one
         *  thread to shared data are visible to other threads.
         */
        @Volatile
        private var INSTANCE: Covid_19DataBase? = null


         /* @param context The application context Singleton, used to get access to the filesystem.
         */
        fun getInstance(context: Context): Covid_19DataBase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this) {

                var instance = INSTANCE
                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        Covid_19DataBase::class.java,
                        "covid_19_database"
                    )
                        .build()
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }
                // Return instance; smart cast to be non-null.
                return instance
            }
        }
    }
}