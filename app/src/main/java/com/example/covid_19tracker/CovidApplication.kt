package com.example.covid_19tracker

import android.app.Application
import androidx.work.*
import com.example.covid_19tracker.repository.Repository
import com.example.covid_19tracker.repository.RepositoryContract
import com.example.covid_19tracker.workManager.RefreshWorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class CovidApplication:Application() {
    val repository: RepositoryContract
        get() = ServiceLocator.provideRepository(this)

    private val appScope =  CoroutineScope(Dispatchers.Default)
    //TODO >> get the setting user defined refresh time from shared preference
    private var REFRESH_TIME:Long = 15
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
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresDeviceIdle(false).build()
        val refreshRequest = PeriodicWorkRequestBuilder<RefreshWorkManager>(REFRESH_TIME,TimeUnit.MINUTES)
            .setConstraints(constraints).build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(RefreshWorkManager.REFRESH_WORKER
            ,ExistingPeriodicWorkPolicy.REPLACE,refreshRequest)
    }
}