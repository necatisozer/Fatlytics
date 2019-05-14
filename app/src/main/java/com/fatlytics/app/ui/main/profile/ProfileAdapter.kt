package com.fatlytics.app.ui.main.profile

import android.view.ViewGroup
import com.fatlytics.app.databinding.ItemProfileBinding
import com.fatlytics.app.extension.getInflater
import com.fatlytics.app.ui.base.BaseAdapter
import com.fatlytics.app.ui.base.BaseViewHolder

class ProfileAdapter : BaseAdapter<ProfileItem, ProfileViewHolder>() {
    override fun onCreateViewHolder(root: ViewGroup) =
        ProfileViewHolder(ItemProfileBinding.inflate(root.getInflater(), root, false))
}

class ProfileViewHolder(binding: ItemProfileBinding) :
    BaseViewHolder<ProfileItem, ItemProfileBinding>(binding) {

    override fun bindData(data: ProfileItem) {
        binding.profileTitle.text = data.title
        binding.profileValue.text = data.value
    }
}

data class ProfileItem(
    val title: String?,
    val value: String?
)