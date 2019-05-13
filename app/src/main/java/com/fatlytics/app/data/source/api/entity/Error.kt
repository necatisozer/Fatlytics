package com.fatlytics.app.data.source.api.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Error(
    val code: Int,
    val message: String
) : ApiEntity