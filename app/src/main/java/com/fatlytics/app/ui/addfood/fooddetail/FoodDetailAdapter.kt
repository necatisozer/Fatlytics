package com.fatlytics.app.ui.addfood.fooddetail

import android.view.ViewGroup
import com.fatlytics.app.R
import com.fatlytics.app.data.source.api.ServingContent
import com.fatlytics.app.databinding.ItemFoodDetailBinding
import com.fatlytics.app.extension.getInflater
import com.fatlytics.app.extension.visible
import com.fatlytics.app.ui.base.BaseAdapter
import com.fatlytics.app.ui.base.BaseViewHolder
import splitties.views.textColorResource
import java.math.RoundingMode

class FoodDetailAdapter : BaseAdapter<ServingContent, FoodDetailViewHolder>() {
    override fun onCreateViewHolder(root: ViewGroup) =
        FoodDetailViewHolder(ItemFoodDetailBinding.inflate(root.getInflater(), root, false))
}

class FoodDetailViewHolder(binding: ItemFoodDetailBinding) :
    BaseViewHolder<ServingContent, ItemFoodDetailBinding>(binding) {

    override fun bindData(data: ServingContent) {
        binding.foodContent.text = StringBuilder().apply {
            if (data.subContent) append("\t\t")
            append(data.name)
        }

        binding.foodContentAmount.text = "${data.amount.toIntString()} ${data.measure}"

        if (data.warning) {
            itemView.setBackgroundResource(R.drawable.error_border)
            binding.foodContent.textColorResource = R.color.colorError
            binding.foodContentAmount.textColorResource = R.color.colorError
            binding.warningIcon.visible()
        }
    }
}

fun Double?.toIntString() = this?.let {
    if (it.toInt().compareTo(it) == 0) it.toInt().toString() else it.round().toString()
}

private fun Double?.round() = this?.let { toBigDecimal().setScale(3, RoundingMode.UP).toDouble() }