package com.fatlytics.app.ui.main.fragment

import android.os.Bundle
import com.fatlytics.app.R
import com.fatlytics.app.databinding.MainFragmentBinding
import com.fatlytics.app.extension.observeNonNull
import com.fatlytics.app.ui.base.BaseViewModelFragment

class MainFragment : BaseViewModelFragment<MainFragmentViewModel, MainFragmentBinding>() {
    override val layoutRes = R.layout.main_fragment
    override val viewModelClass = MainFragmentViewModel::class.java

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() {
    }

    private fun initViewModel() {
        viewModel.getViewState().observeNonNull(viewLifecycleOwner) {
            binding.apply {
                viewState = it
                executePendingBindings()
            }
        }
    }
}
