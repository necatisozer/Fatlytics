package com.fatlytics.app.data.source.api

import com.fatlytics.app.data.source.api.entity.FoodEntriesGet
import com.fatlytics.app.data.source.api.entity.FoodEntryCreate
import com.fatlytics.app.data.source.api.entity.FoodEntryDelete
import com.fatlytics.app.data.source.api.entity.FoodGet
import com.fatlytics.app.data.source.api.entity.FoodsSearch
import com.fatlytics.app.data.source.api.entity.ProfileCreate
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FatlyticsApi {
    @GET("?method=profile.create")
    fun createProfile(@Query("user_id") userId: String? = null): Single<ProfileCreate>

    @GET("?method=foods.search")
    fun searchFoods(
        @Query("search_expression") searchExpression: String? = null,
        @Query("page_number") pageNumber: Int? = null,
        @Query("max_results") maxResults: Int? = null
    ): Single<FoodsSearch>

    @GET("?method=food.get")
    fun getFood(@Query("food_id") foodId: Long): Single<FoodGet>

    @GET("?method=food_entry.create")
    fun createFoodEntry(
        @Query("food_id") foodId: Long,
        @Query("food_entry_name") foodEntryName: String,
        @Query("serving_id") servingId: Long,
        @Query("number_of_units") numberOfUnits: Double,
        @Query("meal") meal: String,
        @Query("date") date: Int? = null
    ): Single<FoodEntryCreate>

    @GET("?method=food_entry.delete")
    fun deleteFoodEntry(@Query("food_entry_id") foodEntryId: Long): Single<FoodEntryDelete>

    @GET("?method=food_entries.get")
    fun getFoodEntries(@Query("date") date: Int? = null): Single<FoodEntriesGet>
}

class FatlyticsApiException(
    val code: Int,
    override val message: String
) : RuntimeException()
