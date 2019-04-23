package com.fatlytics.app.ui.registration.healthinfo

import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.fatlytics.app.R
import com.fatlytics.app.databinding.HealthInfoFragmentBinding
import com.fatlytics.app.domain.entity.DailyActiveness
import com.fatlytics.app.domain.entity.Disease
import com.fatlytics.app.domain.entity.HealthInfo
import com.fatlytics.app.extension.onSingleClick
import com.fatlytics.app.extension.onSingleTouch
import com.fatlytics.app.ui.base.BaseViewModelFragment
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator

class HealthInfoFragment :
    BaseViewModelFragment<HealthInfoViewModel, HealthInfoFragmentBinding>() {
    override val layoutRes = R.layout.health_info_fragment
    override val viewModelClass = HealthInfoViewModel::class.java

    private lateinit var selectedDailyActiveness: DailyActiveness
    private lateinit var selectedDiseases: List<Disease>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        observeViewModel()
    }

    private fun initView() {
        with(binding) {
            dailyActivenessInputEditText.onSingleTouch { showDailyActivenessPicker() }
            diseasesInputEditText.onSingleTouch { showDiseasesPicker() }
            completeRegistrationButton.onSingleClick { validateFields() }
        }
    }

    private fun observeViewModel() {
    }

    private fun showDailyActivenessPicker() {
        MaterialDialog(requireContext()).show {
            title(R.string.daily_activeness)
            listItemsSingleChoice(R.array.daily_activeness) { dialog, index, text ->
                selectedDailyActiveness = when (index) {
                    0 -> DailyActiveness.HIGH
                    1 -> DailyActiveness.MEDIUM
                    2 -> DailyActiveness.LOW
                    3 -> DailyActiveness.NOT_ACTIVE
                    else -> error("Invalid activeness")
                }
                binding.dailyActivenessInputEditText.setText(text)
            }
            lifecycleOwner(viewLifecycleOwner)
        }
    }

    private fun showDiseasesPicker() {
        MaterialDialog(requireContext()).show {
            title(R.string.diseases)
            listItemsMultiChoice(R.array.diseases) { dialog, index, text ->
                selectedDiseases = index.map {
                    when (it) {
                        0 -> Disease.DIABETES
                        1 -> Disease.OBESITY
                        2 -> Disease.HEART
                        else -> error("Invalid disease")
                    }
                }
                binding.diseasesInputEditText.setText(text.joinToString())
            }
            positiveButton(R.string.select)
            lifecycleOwner(viewLifecycleOwner)
        }
    }

    private fun validateFields() {
        with(binding) {
            if (heightInputEditText.validator()
                    .greaterThan(40)
                    .lessThan(300)
                    .addErrorCallback { heightInputEditText.error = it }.check() &&
                weightInputEditText.validator()
                    .greaterThan(2)
                    .lessThan(650)
                    .addErrorCallback { weightInputEditText.error = it }.check() &&
                dailyActivenessInputEditText.nonEmpty { dailyActivenessInputEditText.error = it } &&
                diseasesInputEditText.nonEmpty { diseasesInputEditText.error = it } &&
                this@HealthInfoFragment::selectedDailyActiveness.isInitialized.also {
                    if (it.not()) dailyActivenessInputEditText.error = getString(R.string.error)
                } &&
                this@HealthInfoFragment::selectedDiseases.isInitialized.also {
                    if (it.not()) diseasesInputEditText.error = getString(R.string.error)
                }
            ) {
                HealthInfo(
                    height = heightInputEditText.text.toString().toInt(),
                    weight = weightInputEditText.text.toString().toInt(),
                    dailyActiveness = selectedDailyActiveness,
                    diseases = selectedDiseases
                ).also { viewModel.onHealthInfoInput(it) }
            }
        }
    }
}
