package com.fatlytics.app.ui.addfood

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import com.fatlytics.app.R
import com.fatlytics.app.databinding.AddFoodActivityBinding
import com.fatlytics.app.ui.base.BaseFragmentActivity

class AddFoodActivity : BaseFragmentActivity<AddFoodViewModel, AddFoodActivityBinding>() {
    override val layoutRes = R.layout.add_food_activity
    override val viewModelClass = AddFoodViewModel::class.java
    override val navController: NavController by lazy { findNavController(R.id.container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        observeViewModel()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun initView() {
        navController.setGraph(R.navigation.navigation_add_food, intent.extras)
    }

    private fun observeViewModel() {}
}
