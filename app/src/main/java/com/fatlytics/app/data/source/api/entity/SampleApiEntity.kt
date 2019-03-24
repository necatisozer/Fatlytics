package com.fatlytics.app.data.source.api.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SampleApiEntity(
    val id: Int
) : ApiEntity