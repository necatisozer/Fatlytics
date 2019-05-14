package com.fatlytics.app.ui.main.profile

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatlytics.app.R
import com.fatlytics.app.databinding.ProfileFragmentBinding
import com.fatlytics.app.ui.base.BaseViewModelFragment

class ProfileFragment : BaseViewModelFragment<ProfileViewModel, ProfileFragmentBinding>() {
    override val layoutRes = R.layout.profile_fragment
    override val viewModelClass = ProfileViewModel::class.java

    private val profileAdapter: ProfileAdapter by lazy { ProfileAdapter() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        observeViewModel()
    }

    private fun initView() {
        binding.profileRecycler.apply {
            adapter = profileAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun observeViewModel() {
        viewModel.profileView.observe(viewLifecycleOwner, Observer {
            binding.profileName.text = it.username
            profileAdapter.submitList(it.info)
        })
    }
}
