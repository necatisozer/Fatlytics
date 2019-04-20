package com.fatlytics.app.ui.registration

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.fatlytics.app.R
import com.fatlytics.app.databinding.RegistrationActivityBinding
import com.fatlytics.app.ui.base.BaseFragmentActivity

class RegistrationActivity :
    BaseFragmentActivity<RegistrationViewModel, RegistrationActivityBinding>() {
    override val layoutRes = R.layout.registration_activity
    override val viewModelClass = RegistrationViewModel::class.java
    override val navController: NavController by lazy { findNavController(R.id.container) }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        observeViewModel()
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    private fun initView() {
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun observeViewModel() {}
}
