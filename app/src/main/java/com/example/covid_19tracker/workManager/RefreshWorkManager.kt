package com.example.covid_19tracker.workManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.covid_19tracker.CovidApplication
import com.example.covid_19tracker.utils.Covid_19Notification
import timber.log.Timber

class RefreshWorkManager(val appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext,params) {
    private val CHANNEL_ID = "COVID_19_CHANNEL"
    companion object{
        const val REFRESH_WORKER = "RefreshWorker"
    }

    override suspend fun doWork(): Result {
         val repo = (appContext as CovidApplication).repository
         return try {
             Timber.i("Hello From Work Manager")
             Covid_19Notification.displayApplicationNotification(appContext,CHANNEL_ID,"Hello","Test")
             repo.refreshCountries()
             Result.success()
         } catch (error:Throwable){
             Result.failure()
         }
    }
}