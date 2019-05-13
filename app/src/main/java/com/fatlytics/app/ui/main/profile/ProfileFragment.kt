package com.fatlytics.app.ui.main.profile

import android.os.Bundle
import com.fatlytics.app.R
import com.fatlytics.app.databinding.ProfileFragmentBinding
import com.fatlytics.app.ui.base.BaseViewModelFragment

class ProfileFragment : BaseViewModelFragment<ProfileViewModel, ProfileFragmentBinding>() {
    override val layoutRes = R.layout.profile_fragment
    override val viewModelClass = ProfileViewModel::class.java

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        observeViewModel()
    }

    private fun initView() {
    }

    private fun observeViewModel() {
    }
}
