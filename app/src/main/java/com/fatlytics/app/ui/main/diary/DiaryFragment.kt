package com.fatlytics.app.ui.main.diary

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatlytics.app.R
import com.fatlytics.app.databinding.DiaryFragmentBinding
import com.fatlytics.app.extension.onSingleClick
import com.fatlytics.app.ui.addfood.AddFoodActivity
import com.fatlytics.app.ui.base.BaseViewModelFragment
import splitties.fragments.start

class DiaryFragment : BaseViewModelFragment<DiaryViewModel, DiaryFragmentBinding>() {
    override val layoutRes = R.layout.diary_fragment
    override val viewModelClass = DiaryViewModel::class.java

    private val breakfastFoodEntryAdapter: FoodEntryAdapter by lazy { FoodEntryAdapter() }
    private val lunchFoodEntryAdapter: FoodEntryAdapter by lazy { FoodEntryAdapter() }
    private val dinnerFoodEntryAdapter: FoodEntryAdapter by lazy { FoodEntryAdapter() }
    private val otherFoodEntryAdapter: FoodEntryAdapter by lazy { FoodEntryAdapter() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        observeViewModel()
    }

    private fun initView() {
        binding.breakfastRecycler.apply {
            adapter = breakfastFoodEntryAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        binding.lunchRecycler.apply {
            adapter = lunchFoodEntryAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        binding.dinnerRecycler.apply {
            adapter = dinnerFoodEntryAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        binding.otherRecycler.apply {
            adapter = otherFoodEntryAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        binding.breakfastAddFood.onSingleClick {
            start<AddFoodActivity> {
                putExtra("meal", "breakfast")
            }
        }

        binding.lunchAddFood.onSingleClick {
            start<AddFoodActivity> {
                putExtra("meal", "lunch")
            }
        }

        binding.dinnerAddFood.onSingleClick {
            start<AddFoodActivity> {
                putExtra("meal", "dinner")
            }
        }

        binding.otherAddFood.onSingleClick {
            start<AddFoodActivity> {
                putExtra("meal", "other")
            }
        }
    }

    private fun observeViewModel() {
        viewModel.init()
        viewModel.breakfastFoodEntries.observe(viewLifecycleOwner, Observer {
            breakfastFoodEntryAdapter.submitList(it.breakfast)
            lunchFoodEntryAdapter.submitList(it.lunch)
            dinnerFoodEntryAdapter.submitList(it.dinner)
            otherFoodEntryAdapter.submitList(it.other)
            binding.breakfastTotalCalories.text =
                getString(R.string.cal, it.breakfastTotalCal.toString())
        })
    }
}
