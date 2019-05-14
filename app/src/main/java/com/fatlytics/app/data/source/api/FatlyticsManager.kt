package com.fatlytics.app.data.source.api

import com.fatlytics.app.data.source.api.entity.FoodEntriesGet
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FatlyticsManager @Inject constructor(
    private val fatlyticsApi: FatlyticsApi
) {
    fun getFood(id: Long, amount: Double): Single<Food> {
        return fatlyticsApi.getFood(id).map {
            Food(
                id = it.food?.food_id?.toLongOrNull(),
                name = it.food?.food_name,
                servings = it.food?.servings?.serving?.map {
                    Serving(
                        id = it.serving_id?.toLongOrNull(),
                        numberOfUnits = it.number_of_units?.toDoubleOrNull()?.times(amount),
                        measurementDescription = it.measurement_description,
                        contents = listOf(
                            ServingContent(
                                name = "Calories",
                                amount = it.calories?.toDoubleOrNull()?.times(amount),
                                measure = "cal"
                            ),
                            ServingContent(
                                name = "Total Fat",
                                amount = it.fat?.toDoubleOrNull()?.times(amount),
                                measure = "g"
                            ),
                            ServingContent(
                                name = "Saturated Fat",
                                amount = it.saturated_fat?.toDoubleOrNull()?.times(amount),
                                measure = "g",
                                subContent = true
                            ),
                            ServingContent(
                                name = "Polyunsaturated Fat",
                                amount = it.polyunsaturated_fat?.toDoubleOrNull()?.times(amount),
                                measure = "g",
                                subContent = true
                            ),
                            ServingContent(
                                name = "Monounsaturated Fat",
                                amount = it.monounsaturated_fat?.toDoubleOrNull()?.times(amount),
                                measure = "g",
                                subContent = true
                            ),
                            ServingContent(
                                name = "Cholesterol",
                                amount = it.cholesterol?.toDoubleOrNull()?.times(amount),
                                measure = "mg"
                            ),
                            ServingContent(
                                name = "Sodium",
                                amount = it.sodium?.toDoubleOrNull()?.times(amount),
                                measure = "mg"
                            ),
                            ServingContent(
                                name = "Potassium",
                                amount = it.potassium?.toDoubleOrNull()?.times(amount),
                                measure = "mg"
                            ),
                            ServingContent(
                                name = "Total Carbohydrate",
                                amount = it.carbohydrate?.toDoubleOrNull()?.times(amount),
                                measure = "g"
                            ),
                            ServingContent(
                                name = "Dietary Fiber",
                                amount = it.fiber?.toDoubleOrNull()?.times(amount),
                                measure = "g",
                                subContent = true
                            ),
                            ServingContent(
                                name = "Sugars",
                                amount = it.sugar?.toDoubleOrNull()?.times(amount),
                                measure = "g",
                                subContent = true
                            ),
                            ServingContent(
                                name = "Protein",
                                amount = it.protein?.toDoubleOrNull()?.times(amount),
                                measure = "g"
                            ),
                            ServingContent(
                                name = "Vitamin A",
                                amount = it.vitamin_a?.toDoubleOrNull(),
                                measure = "%"
                            ),
                            ServingContent(
                                name = "Vitamin C",
                                amount = it.vitamin_c?.toDoubleOrNull(),
                                measure = "%"
                            ),
                            ServingContent(
                                name = "Calcium",
                                amount = it.calcium?.toDoubleOrNull(),
                                measure = "%"
                            ),
                            ServingContent(
                                name = "Iron",
                                amount = it.iron?.toDoubleOrNull(),
                                measure = "%"
                            )
                        )
                    )
                }
            )
        }
    }

    fun getFoodEntries(): Single<FoodEntriesModel> {
        return fatlyticsApi.getFoodEntries().map {
            it.food_entries?.food_entry?.let {
                val foodEntries = it.groupBy { it.meal }
                FoodEntriesModel(
                    breakfast = foodEntries["Breakfast"].let { it } ?: listOf(),
                    breakfastTotalCal = foodEntries["Breakfast"]?.let { it.sumBy { it.calories.toIntOrZero() } }
                        ?: 0,
                    lunch = foodEntries["Lunch"].let { it } ?: listOf(),
                    lunchTotalCal = foodEntries["Lunch"]?.let { it.sumBy { it.calories.toIntOrZero() } }
                        ?: 0,
                    dinner = foodEntries["Dinner"].let { it } ?: listOf(),
                    dinnerTotalCal = foodEntries["Dinner"]?.let { it.sumBy { it.calories.toIntOrZero() } }
                        ?: 0,
                    other = foodEntries["Other"].let { it } ?: listOf(),
                    otherTotalCal = foodEntries["Other"]?.let { it.sumBy { it.calories.toIntOrZero() } }
                        ?: 0
                )
            } ?: FoodEntriesModel()
        }
    }
}

data class Food(
    val id: Long?,
    val name: String?,
    val servings: List<Serving>?
)

data class Serving(
    val id: Long?,
    val numberOfUnits: Double?,
    val measurementDescription: String?,
    val contents: List<ServingContent?>
)

data class ServingContent(
    val name: String?,
    val amount: Double?,
    val measure: String,
    val subContent: Boolean = false,
    val warning: Boolean = false,
    val warningReason: String? = null
)

data class FoodEntriesModel(
    val breakfast: List<FoodEntriesGet.FoodEntries.FoodEntry> = listOf(),
    val breakfastTotalCal: Int = 0,
    val lunch: List<FoodEntriesGet.FoodEntries.FoodEntry> = listOf(),
    val lunchTotalCal: Int = 0,
    val dinner: List<FoodEntriesGet.FoodEntries.FoodEntry> = listOf(),
    val dinnerTotalCal: Int = 0,
    val other: List<FoodEntriesGet.FoodEntries.FoodEntry> = listOf(),
    val otherTotalCal: Int = 0
) {
    fun getTotalCal() = breakfastTotalCal + lunchTotalCal + dinnerTotalCal + otherTotalCal
}

private fun String?.toIntOrZero() = this?.toIntOrNull()?.let { it } ?: 0