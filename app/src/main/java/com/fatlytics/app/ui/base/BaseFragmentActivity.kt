package com.fatlytics.app.ui.base

import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController

abstract class BaseFragmentActivity<M : BaseViewModel, B : ViewDataBinding> :
    BaseViewActivity<M, B>() {
    protected abstract val navController: NavController
    protected lateinit var appBarConfiguration: AppBarConfiguration

    override fun onStart() {
        super.onStart()
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}