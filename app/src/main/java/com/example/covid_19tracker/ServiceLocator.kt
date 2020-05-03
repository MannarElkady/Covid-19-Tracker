package com.example.covid_19tracker

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.example.covid_19tracker.database.CovidDao
import com.example.covid_19tracker.database.LocalDataSource
import com.example.covid_19tracker.network.RemoteDataSource
import com.example.covid_19tracker.repository.Repository
import com.example.covid_19tracker.repository.RepositoryContract

object ServiceLocator {
    private val lock = Any()

    @Volatile
    var repository: RepositoryContract? = null
        @VisibleForTesting set
    fun provideRepository(context: Context): RepositoryContract {
        synchronized(this) {
            return repository ?: createTasksRepository(context)
        }
    }

    private fun createTasksRepository(context: Context): RepositoryContract {
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