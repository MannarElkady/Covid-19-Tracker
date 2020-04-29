package com.example.covid_19tracker.WorkManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class RefreshWorkManager(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        TODO("Not yet implemented")
    }
}