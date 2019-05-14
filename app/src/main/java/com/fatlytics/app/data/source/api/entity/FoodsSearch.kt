package com.fatlytics.app.data.source.api.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodsSearch(
    val foods: Foods?,
    val error: Error?
) : ApiEntity {
    @JsonClass(generateAdapter = true)
    data class Foods(
        val food: List<Food>?,
        val max_results: String?,
        val page_number: String?,
        val total_results: String?
    ) : ApiEntity {
        @JsonClass(generateAdapter = true)
        data class Food(
            val brand_name: String?,
            val food_description: String?,
            val food_id: String?,
            val food_name: String?,
            val food_type: String?,
            val food_url: String?
        ) : ApiEntity
    }
}