package com.fatlytics.app.ui.main.home

import android.os.Bundle
import com.fatlytics.app.R
import com.fatlytics.app.databinding.HomeFragmentBinding
import com.fatlytics.app.ui.base.BaseViewModelFragment

class HomeFragment : BaseViewModelFragment<HomeViewModel, HomeFragmentBinding>() {
    override val layoutRes = R.layout.home_fragment
    override val viewModelClass = HomeViewModel::class.java

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
