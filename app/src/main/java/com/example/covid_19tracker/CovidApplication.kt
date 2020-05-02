package com.example.covid_19tracker

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.covid_19tracker.Repository.Repository
import com.example.covid_19tracker.WorkManager.RefreshWorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import timber.log.Timber
import java.util.concurrent.TimeUnit

class CovidApplication:Application() {
    val repository: Repository
        get() = ServiceLocator.provideRepository(this)

    private val appScope =  CoroutineScope(Dispatchers.Default)
    //TODO >> get the setting user defined refresh time from shared preference
    private var REFRESH_TIME:Long = 2
    set(value) {
        field = value
    }
    override fun onCreate() {
        //Run once application is intialized and before first screen appears
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        initRefreshWorker()
    }
    private fun initRefreshWorker(){
        appScope.launch {
            setUpRefreshWorker()
        }
    }
    fun setUpRefreshWorker(){
        val constraints = Constraints.Builder().setRequiresBatteryNotLow(true)
            .setRequiresDeviceIdle(false).build()
        val refreshRequest = PeriodicWorkRequestBuilder<RefreshWorkManager>(REFRESH_TIME,TimeUnit.MINUTES)
            .setConstraints(constraints).build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(RefreshWorkManager.REFRESH_WORKER
            ,ExistingPeriodicWorkPolicy.REPLACE,refreshRequest)
    }
}