package com.fatlytics.app.data.source.api.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodEntryDelete(
    val success: Success?,
    val error: Error?
) : ApiEntity {
    @JsonClass(generateAdapter = true)
    data class Success(
        val value: String?
    ) : ApiEntity
}