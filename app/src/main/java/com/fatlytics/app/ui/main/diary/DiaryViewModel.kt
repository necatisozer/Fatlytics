package com.fatlytics.app.ui.main.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatlytics.app.data.source.api.FatlyticsApi
import com.fatlytics.app.data.source.api.entity.FoodEntriesGet
import com.fatlytics.app.extension.doInBackground
import com.fatlytics.app.ui.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class DiaryViewModel @Inject constructor(
    private val fatlyticsApi: FatlyticsApi
) : BaseViewModel() {
    private val mBreakfastFoodEntries = MutableLiveData<FoodEntriesModel>()
    val breakfastFoodEntries: LiveData<FoodEntriesModel> get() = mBreakfastFoodEntries

    fun init() {
        fatlyticsApi.getFoodEntries()
            .map {
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
            .doInBackground()
            .subscribeBy { mBreakfastFoodEntries.value = it }.also { disposables += it }
    }
}

data class FoodEntriesModel(
    val breakfast: List<FoodEntriesGet.FoodEntries.FoodEntry> = listOf(),
    val breakfastTotalCal: Int = 0,
    val lunch: List<FoodEntriesGet.FoodEntries.FoodEntry> = listOf(),
    val lunchTotalCal: Int = 0,
    val dinner: List<FoodEntriesGet.FoodEntries.FoodEntry> = listOf(),
    val dinnerTotalCal: Int = 0,
    val other: List<FoodEntriesGet.FoodEntries.FoodEntry> = listOf(),
    val otherTotalCal: Int = 0

)

private fun String?.toIntOrZero() = this?.toIntOrNull()?.let { it } ?: 0