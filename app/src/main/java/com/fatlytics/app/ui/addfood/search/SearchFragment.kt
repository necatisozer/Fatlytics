package com.fatlytics.app.ui.addfood.search

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatlytics.app.R
import com.fatlytics.app.databinding.SearchFragmentBinding
import com.fatlytics.app.extension.onQueryChange
import com.fatlytics.app.ui.base.BaseViewModelFragment

class SearchFragment : BaseViewModelFragment<SearchViewModel, SearchFragmentBinding>() {
    override val layoutRes = R.layout.search_fragment
    override val viewModelClass = SearchViewModel::class.java

    private val args by navArgs<SearchFragmentArgs>()

    private val foodSearchAdapter: FoodSearchAdapter by lazy {
        FoodSearchAdapter().apply {
            clickListener = {
                SearchFragmentDirections.actionSearchFragmentToFoodDetailFragment(
                    args.meal,
                    it.food_id!!.toLong()
                ).also { findNavController().navigate(it) }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        observeViewModel()
    }

    private fun initView() {
        binding.foodSearchRecycler.apply {
            adapter = foodSearchAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        binding.foodSearchView.apply {
            onQueryChange { viewModel.onQueryChange(it) }
            requestFocus()
        }
    }

    private fun observeViewModel() {
        viewModel.searchResultsLiveData.observe(viewLifecycleOwner, Observer {
            foodSearchAdapter.submitList(it)
        })
    }
}
