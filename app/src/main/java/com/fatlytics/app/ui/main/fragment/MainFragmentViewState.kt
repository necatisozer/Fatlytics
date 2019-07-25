package com.fatlytics.app.ui.main.fragment

import android.content.Context
import com.fatlytics.app.R
import com.fatlytics.app.data.repository.entity.SampleEntity
import com.fatlytics.app.helper.Resource

class MainFragmentViewState(
    private val sampleEntityResource: Resource<SampleEntity>
) {
    val sampleEntityId = when (sampleEntityResource) {
        is Resource.Success -> sampleEntityResource.data.id.toString()
        else -> null
    }

    val loading: Boolean = sampleEntityResource is Resource.Loading

    val showErrorMessage = sampleEntityResource is Resource.Error

    fun getSampleEntityErrorMessage(context: Context) = when (sampleEntityResource) {
        is Resource.Error -> context.getString(R.string.sample_entity_error_message)
        else -> null
    }
}