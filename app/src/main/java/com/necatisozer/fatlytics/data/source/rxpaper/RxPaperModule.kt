package com.necatisozer.fatlytics.data.source.rxpaper

import com.pacoworks.rxpaper2.RxPaperBook
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class RxPaperModule {
    @Singleton
    @Provides
    @RxSampleBook
    fun provideSampleBook() = RxPaperBook.with("sample")
}

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class RxSampleBook