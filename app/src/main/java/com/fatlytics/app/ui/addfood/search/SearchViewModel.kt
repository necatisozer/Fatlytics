package com.fatlytics.app.ui.addfood.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatlytics.app.data.source.api.FatlyticsApi
import com.fatlytics.app.data.source.api.entity.FoodsSearch
import com.fatlytics.app.extension.doInBackground
import com.fatlytics.app.ui.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val fatlyticsApi: FatlyticsApi
) : BaseViewModel() {
    private val mSearchResultsLiveData = MutableLiveData<List<FoodsSearch.Foods.Food>>()
    val searchResultsLiveData: LiveData<List<FoodsSearch.Foods.Food>> get() = mSearchResultsLiveData

    fun onQueryChange(query: String) {
        fatlyticsApi.searchFoods(query).doInBackground().subscribeBy {
            it.foods?.food.let { mSearchResultsLiveData.value = it }
        }.also { disposables += it }
    }
}