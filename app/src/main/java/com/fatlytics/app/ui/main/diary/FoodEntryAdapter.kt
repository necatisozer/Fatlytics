package com.fatlytics.app.ui.main.diary

import android.view.ViewGroup
import com.fatlytics.app.R
import com.fatlytics.app.data.source.api.entity.FoodEntriesGet
import com.fatlytics.app.databinding.ItemFoodEntryBinding
import com.fatlytics.app.extension.getInflater
import com.fatlytics.app.ui.base.BaseAdapter
import com.fatlytics.app.ui.base.BaseViewHolder

class FoodEntryAdapter : BaseAdapter<FoodEntriesGet.FoodEntries.FoodEntry, FoodEntryViewHolder>() {
    override fun onCreateViewHolder(root: ViewGroup) =
        FoodEntryViewHolder(ItemFoodEntryBinding.inflate(root.getInflater(), root, false))
}

class FoodEntryViewHolder(binding: ItemFoodEntryBinding) :
    BaseViewHolder<FoodEntriesGet.FoodEntries.FoodEntry, ItemFoodEntryBinding>(binding) {

    override fun bindData(data: FoodEntriesGet.FoodEntries.FoodEntry) {
        binding.description.text = data.food_entry_description
        binding.calories.text = context.getString(R.string.cal, data.calories)
    }
}