package com.recall.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.recall.app.core.analytics.AnalyticsManager
import com.recall.app.sync.SyncScheduler
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class RecallApplication : Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory
    @Inject lateinit var syncScheduler: SyncScheduler
    @Inject lateinit var analyticsManager: AnalyticsManager

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        // Startup must never take the whole app down. Optional services are
        // best-effort; the local Room/Compose experience remains available.
        runCatching { analyticsManager.initialize() }
            .onFailure { Timber.e(it, "Analytics initialization skipped") }

        runCatching { syncScheduler.scheduleNightlyResurfacing() }
            .onFailure { Timber.e(it, "Nightly resurfacing scheduling skipped") }

        Timber.d("Recall application started")
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
