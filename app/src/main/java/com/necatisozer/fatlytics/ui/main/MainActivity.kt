package com.necatisozer.fatlytics.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.necatisozer.fatlytics.R
import com.necatisozer.fatlytics.databinding.MainActivityBinding
import com.necatisozer.fatlytics.ui.base.BaseFragmentActivity

class MainActivity : BaseFragmentActivity<MainViewModel, MainActivityBinding>() {
    override val layoutRes = R.layout.main_activity
    override val viewModelClass = MainViewModel::class.java
    override val navController: NavController by lazy { findNavController(R.id.container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() {}
    private fun initViewModel() {}
}
