package com.necatisozer.fatlytics.data.source.api

import com.necatisozer.fatlytics.data.source.api.entity.SampleApiEntity
import io.reactivex.Single
import retrofit2.http.GET

interface Api {
    @GET("sample/endpoint")
    fun getSample(): Single<SampleApiEntity>
}