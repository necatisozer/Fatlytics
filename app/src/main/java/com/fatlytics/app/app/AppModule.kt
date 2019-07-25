package com.fatlytics.app.app

import com.fatlytics.app.app.initializer.AppInitializerModule
import com.fatlytics.app.data.repository.RepositoryModule
import com.fatlytics.app.helper.Logger
import com.fatlytics.app.helper.TimberLogger
import com.fatlytics.app.ui.ActivityModule
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(
    includes = [
        AppInitializerModule::class,
        ActivityModule::class,
        RepositoryModule::class
    ]
)
abstract class AppModule {
    @Singleton
    @Binds
    abstract fun bindLogger(timberLogger: TimberLogger): Logger
}