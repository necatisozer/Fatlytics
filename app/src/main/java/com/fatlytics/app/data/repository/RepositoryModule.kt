package com.fatlytics.app.data.repository

import com.fatlytics.app.data.source.api.ApiModule
import com.fatlytics.app.data.source.rxpaper.RxPaperModule
import com.fatlytics.app.domain.repository.SampleRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [ApiModule::class, RxPaperModule::class])
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindSampleRepository(prodSampleRepository: ProdSampleRepository): SampleRepository
}