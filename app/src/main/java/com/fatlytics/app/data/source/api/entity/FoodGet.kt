package com.fatlytics.app.data.source.api.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodGet(
    val food: Food?,
    val error: Error?
) : ApiEntity {
    @JsonClass(generateAdapter = true)
    data class Food(
        val food_id: String?,
        val food_name: String?,
        val food_type: String?,
        val food_url: String?,
        val servings: Servings?
    ) : ApiEntity {
        @JsonClass(generateAdapter = true)
        data class Servings(
            val serving: List<Serving>?
        ) : ApiEntity {
            @JsonClass(generateAdapter = true)
            data class Serving(
                val calcium: String?,
                val calories: String?,
                val carbohydrate: String?,
                val cholesterol: String?,
                val fat: String?,
                val fiber: String?,
                val iron: String?,
                val measurement_description: String?,
                val metric_serving_amount: String?,
                val metric_serving_unit: String?,
                val monounsaturated_fat: String?,
                val number_of_units: String?,
                val polyunsaturated_fat: String?,
                val potassium: String?,
                val protein: String?,
                val saturated_fat: String?,
                val serving_description: String?,
                val serving_id: String?,
                val serving_url: String?,
                val sodium: String?,
                val sugar: String?,
                val vitamin_a: String?,
                val vitamin_c: String?
            ) : ApiEntity
        }
    }
}