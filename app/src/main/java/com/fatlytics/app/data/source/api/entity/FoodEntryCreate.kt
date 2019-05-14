package com.fatlytics.app.data.source.api.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodEntryCreate(
    val food_entry_id: FoodEntryId?,
    val error: Error?
) : ApiEntity {
    @JsonClass(generateAdapter = true)
    data class FoodEntryId(
        val value: String?
    ) : ApiEntity
}