package com.example.covid_19tracker.WorkManager

import android.app.Application
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.covid_19tracker.Database.LocalDataSource
import com.example.covid_19tracker.Network.RemoteDataSource
import com.example.covid_19tracker.Repository.Repository
import timber.log.Timber

class RefreshWorkManager(val appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext,params) {
    companion object{
        const val REFRESH_WORKER = "RefreshWorker"
    }

    override suspend fun doWork(): Result {
        val remoteDataSource = RemoteDataSource
        val localDataSource= LocalDataSource(context = appContext)
         val repo = Repository(remoteDataSource=remoteDataSource,localDataSource = localDataSource)
         return try {
             Timber.i("Hello From Work Manager")
             repo.refreshCountries()
             Result.success()
         } catch (error:Throwable){
             Result.failure()
         }
    }
}