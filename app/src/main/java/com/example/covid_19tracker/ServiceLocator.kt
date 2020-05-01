package com.example.covid_19tracker

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.example.covid_19tracker.Database.CovidDao
import com.example.covid_19tracker.Database.LocalDataSource
import com.example.covid_19tracker.Network.RemoteDataSource
import com.example.covid_19tracker.Repository.Repository

object ServiceLocator {
    private val lock = Any()

    @Volatile
    var repository: Repository? = null
        @VisibleForTesting set
    fun provideRepository(context: Context): Repository {
        synchronized(this) {
            return repository ?: createTasksRepository(context)
        }
    }

    private fun createTasksRepository(context: Context): Repository {
        val newRepo = Repository(RemoteDataSource, createTaskLocalDataSource(context))
        repository = newRepo
        return newRepo
    }

    private fun createTaskLocalDataSource(context: Context): CovidDao {
        return LocalDataSource(context)
    }

    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            repository = null
        }
    }
}