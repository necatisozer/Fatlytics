package com.fatlytics.app.ui.addfood.search

import android.view.ViewGroup
import com.fatlytics.app.data.source.api.entity.FoodsSearch
import com.fatlytics.app.databinding.ItemFoodSearchBinding
import com.fatlytics.app.extension.getInflater
import com.fatlytics.app.ui.base.BaseAdapter
import com.fatlytics.app.ui.base.BaseViewHolder

class FoodSearchAdapter : BaseAdapter<FoodsSearch.Foods.Food, SearchViewHolder>() {
    override fun onCreateViewHolder(root: ViewGroup) =
        SearchViewHolder(ItemFoodSearchBinding.inflate(root.getInflater(), root, false))
}

class SearchViewHolder(binding: ItemFoodSearchBinding) :
    BaseViewHolder<FoodsSearch.Foods.Food, ItemFoodSearchBinding>(binding) {

    override fun bindData(data: FoodsSearch.Foods.Food) {
        val foodName = StringBuilder().apply {
            data.brand_name?.let { append("$it ") }
            data.food_name?.let { append(it) }
        }

        binding.foodName.text = foodName
        binding.foodDescription.text = data.food_description
    }
}