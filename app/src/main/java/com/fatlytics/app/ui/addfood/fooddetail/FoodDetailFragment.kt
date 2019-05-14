package com.fatlytics.app.ui.addfood.fooddetail

import android.os.Bundle
import android.text.InputType
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItems
import com.fatlytics.app.R
import com.fatlytics.app.data.source.api.Food
import com.fatlytics.app.databinding.FoodDetailFragmentBinding
import com.fatlytics.app.extension.onSingleClick
import com.fatlytics.app.ui.base.BaseViewModelFragment

class FoodDetailFragment : BaseViewModelFragment<FoodDetailViewModel, FoodDetailFragmentBinding>() {
    override val layoutRes = R.layout.food_detail_fragment
    override val viewModelClass = FoodDetailViewModel::class.java

    private val args by navArgs<FoodDetailFragmentArgs>()
    private val foodDetailAdapter: FoodDetailAdapter by lazy { FoodDetailAdapter() }

    private var servingDialog: MaterialDialog? = null
    private var food: Food? = null
    private var servingId: Long? = null
    private var servingAmount: Double = 1.0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        observeViewModel()
    }

    private fun initView() {
        binding.foodDetailRecycler.apply {
            adapter = foodDetailAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        binding.servingChangeButton.onSingleClick { servingDialog?.show() }
        binding.addFoodButton.onSingleClick {
            food?.name?.let { foodName ->
                servingId?.let { servingId ->
                    viewModel.addFood(
                        foodId = args.foodId,
                        entryName = foodName,
                        servingId = servingId,
                        numberOfUnits = servingAmount,
                        meal = args.meal
                    )
                }

            }

        }
    }

    private fun observeViewModel() {
        viewModel.food.observe(viewLifecycleOwner, Observer {
            food = it
            val serving = if (servingId == null)
                it.servings?.get(0).also { servingId = it?.id }
            else
                it.servings?.find { it.id == servingId }

            binding.foodName.text = it.name
            binding.foodServing.text = getString(
                R.string.serving,
                serving?.numberOfUnits.toIntString(),
                serving?.measurementDescription
            )
            foodDetailAdapter.submitList(serving?.contents)

            val servingList =
                it.servings?.map { "${(it.numberOfUnits?.div(servingAmount)).toIntString()} ${it.measurementDescription}" }

            servingDialog =
                MaterialDialog(requireContext())
                    .message(text = "Serving amount:")
                    .input(
                        inputType = InputType.TYPE_CLASS_NUMBER,
                        prefill = servingAmount.toIntString(),
                        waitForPositiveButton = false
                    ) { dialog, text ->
                        text.toString().toDoubleOrNull()?.let { servingAmount = it }
                    }
                    .listItems(items = servingList) { dialog, index, text ->
                        servingId = it.servings?.get(index)?.id
                        getFood()
                    }.lifecycleOwner(viewLifecycleOwner)
        })

        viewModel.foodAddCompleteEvent.observe(viewLifecycleOwner, Observer {
            activity?.finish()
        })

        getFood()
    }

    private fun getFood() {
        viewModel.getFood(args.foodId, servingAmount)
    }
}
