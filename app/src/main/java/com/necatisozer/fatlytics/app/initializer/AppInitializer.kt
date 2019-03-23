package com.necatisozer.fatlytics.app.initializer

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}