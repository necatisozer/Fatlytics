package com.fatlytics.app.data.source.api.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodEntriesGet(
    val food_entries: FoodEntries?,
    val error: Error?
) : ApiEntity {
    @JsonClass(generateAdapter = true)
    data class FoodEntries(
        val food_entry: List<FoodEntry>?
    ) : ApiEntity {
        @JsonClass(generateAdapter = true)
        data class FoodEntry(
            val calcium: String?,
            val calories: String?,
            val carbohydrate: String?,
            val cholesterol: String?,
            val date_int: String?,
            val fat: String?,
            val fiber: String?,
            val food_entry_description: String?,
            val food_entry_id: String?,
            val food_entry_name: String?,
            val food_id: String?,
            val iron: String?,
            val meal: String?,
            val monounsaturated_fat: String?,
            val number_of_units: String?,
            val polyunsaturated_fat: String?,
            val potassium: String?,
            val protein: String?,
            val saturated_fat: String?,
            val serving_id: String?,
            val sodium: String?,
            val sugar: String?,
            val vitamin_a: String?,
            val vitamin_c: String?
        ) : ApiEntity
    }
}