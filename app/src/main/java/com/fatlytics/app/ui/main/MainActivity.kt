package com.fatlytics.app.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.fatlytics.app.R
import com.fatlytics.app.databinding.MainActivityBinding
import com.fatlytics.app.ui.base.BaseFragmentActivity

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
