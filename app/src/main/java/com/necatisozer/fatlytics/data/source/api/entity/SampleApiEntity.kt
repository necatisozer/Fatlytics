package com.necatisozer.fatlytics.data.source.api.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SampleApiEntity(
    val id: Int
) : ApiEntity