package com.fatlytics.app.data.source.api

import com.fatlytics.app.data.source.api.entity.ProfileCreate
import io.reactivex.Single
import retrofit2.http.GET

interface FatlyticsApi {
    @GET("?method=profile.create")
    fun createProfile(): Single<ProfileCreate>
}

class FatlyticsApiException(
    val code: Int,
    override val message: String
) : RuntimeException()
