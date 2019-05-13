package com.fatlytics.app.ui.main.diary

import android.os.Bundle
import com.fatlytics.app.R
import com.fatlytics.app.databinding.DiaryFragmentBinding
import com.fatlytics.app.ui.base.BaseViewModelFragment

class DiaryFragment : BaseViewModelFragment<DiaryViewModel, DiaryFragmentBinding>() {
    override val layoutRes = R.layout.diary_fragment
    override val viewModelClass = DiaryViewModel::class.java

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
