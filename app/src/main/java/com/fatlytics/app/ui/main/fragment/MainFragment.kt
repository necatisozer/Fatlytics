package com.fatlytics.app.ui.main.fragment

import android.os.Bundle
import com.fatlytics.app.R
import com.fatlytics.app.databinding.MainFragmentBinding
import com.fatlytics.app.ui.base.BaseViewModelFragment
import splitties.views.textResource

class MainFragment : BaseViewModelFragment<MainFragmentViewModel, MainFragmentBinding>() {
    override val layoutRes = R.layout.main_fragment
    override val viewModelClass = MainFragmentViewModel::class.java

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() {
        binding.sampleTextView.textResource = R.string.app_name
    }

    private fun initViewModel() {
    }
}
