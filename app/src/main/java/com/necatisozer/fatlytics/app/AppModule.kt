package com.necatisozer.fatlytics.app

import android.app.Application
import android.content.Context
import com.necatisozer.fatlytics.app.initializer.AppInitializerModule
import com.necatisozer.fatlytics.data.repository.RepositoryModule
import com.necatisozer.fatlytics.helper.Logger
import com.necatisozer.fatlytics.helper.TimberLogger
import com.necatisozer.fatlytics.ui.ActivityModule
import dagger.Binds
import dagger.Module
import javax.inject.Qualifier
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
    abstract fun bindApplication(application: App): Application

    @Singleton
    @Binds
    @ApplicationContext
    abstract fun bindApplicationContext(application: Application): Context

    @Singleton
    @Binds
    abstract fun bindLogger(timberLogger: TimberLogger): Logger
}

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext