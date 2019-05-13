package com.fatlytics.app.data.source.paper

import dagger.Module
import dagger.Provides
import io.paperdb.Book
import io.paperdb.Paper
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class PaperModule {
    @Singleton
    @Provides
    @AuthBook
    fun provideAuthBook(): Book = Paper.book("auth")

    @Singleton
    @Provides
    @RegistrationBook
    fun provideRegistrationBook(): Book = Paper.book("registration")
}

object AuthBookKeys {
    const val AUTH_TOKEN = "AUTH_TOKEN"
    const val AUTH_SECRET = "AUTH_SECRET"
}

object RegistrationBookKeys {
    const val PERSONAL_INFO = "PERSONAL_INFO"
}

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class AuthBook

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class RegistrationBook

