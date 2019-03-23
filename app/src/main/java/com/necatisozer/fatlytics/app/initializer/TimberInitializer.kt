package com.necatisozer.fatlytics.app.initializer

import android.app.Application
import com.necatisozer.fatlytics.BuildConfig
import com.necatisozer.fatlytics.helper.TimberLogger
import javax.inject.Inject

class TimberInitializer @Inject constructor(
    private val timberLogger: TimberLogger
) : AppInitializer {
    override fun init(application: Application) {
        timberLogger.setup(BuildConfig.DEBUG)
    }
}