package com.fatlytics.app.data.repository

import com.fatlytics.app.data.source.api.ApiModule
import com.fatlytics.app.data.source.rxpaper.RxPaperModule
import dagger.Module

@Module(includes = [ApiModule::class, RxPaperModule::class])
abstract class RepositoryModule