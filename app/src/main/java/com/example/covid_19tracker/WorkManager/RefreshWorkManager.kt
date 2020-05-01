package com.example.covid_19tracker.WorkManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.covid_19tracker.CovidApplication
import timber.log.Timber

class RefreshWorkManager(val appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext,params) {
    companion object{
        const val REFRESH_WORKER = "RefreshWorker"
    }

    override suspend fun doWork(): Result {
         val repo = (appContext as CovidApplication).repository
         return try {
             Timber.i("Hello From Work Manager")
             repo.refreshCountries()
             Result.success()
         } catch (error:Throwable){
             Result.failure()
         }
    }
}