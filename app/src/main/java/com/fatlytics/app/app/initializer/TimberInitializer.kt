package com.fatlytics.app.app.initializer

import android.app.Application
import com.fatlytics.app.BuildConfig
import com.fatlytics.app.helper.TimberLogger
import javax.inject.Inject

class TimberInitializer @Inject constructor(
    private val timberLogger: TimberLogger
) : AppInitializer {
    override fun init(application: Application) {
        timberLogger.setup(BuildConfig.DEBUG)
    }
}