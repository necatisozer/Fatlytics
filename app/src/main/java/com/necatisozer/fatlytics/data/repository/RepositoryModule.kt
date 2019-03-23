package com.necatisozer.fatlytics.data.repository

import com.necatisozer.fatlytics.data.source.api.ApiModule
import com.necatisozer.fatlytics.data.source.rxpaper.RxPaperModule
import com.necatisozer.fatlytics.domain.repository.SampleRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [ApiModule::class, RxPaperModule::class])
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindSampleRepository(prodSampleRepository: ProdSampleRepository): SampleRepository
}