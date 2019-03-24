package com.fatlytics.app.app.initializer

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}