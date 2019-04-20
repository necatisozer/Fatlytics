package com.fatlytics.app.ui.registration.personalinfo

import android.os.Bundle
import android.text.format.DateFormat
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.fatlytics.app.R
import com.fatlytics.app.databinding.PersonalInfoFragmentBinding
import com.fatlytics.app.domain.entity.Gender
import com.fatlytics.app.domain.entity.PersonalInfo
import com.fatlytics.app.extension.onSingleClick
import com.fatlytics.app.extension.onSingleTouch
import com.fatlytics.app.extension.toLocalDate
import com.fatlytics.app.ui.base.BaseViewModelFragment
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import java.util.Calendar

class PersonalInfoFragment :
    BaseViewModelFragment<PersonalInfoViewModel, PersonalInfoFragmentBinding>() {
    override val layoutRes = R.layout.personal_info_fragment
    override val viewModelClass = PersonalInfoViewModel::class.java

    private lateinit var selectedBirthday: Calendar
    private lateinit var selectedGender: Gender

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        observeViewModel()
    }

    private fun initView() {
        with(binding) {
            birthdayInputEditText.onSingleTouch { showDatePicker() }
            genderInputEditText.onSingleTouch { showGenderPicker() }
            confirmButton.onSingleClick { validateFields() }
        }
    }

    private fun observeViewModel() {
        viewModel.usernameAlreadyTakenEvent.observe(viewLifecycleOwner, Observer {
            binding.usernameInputEditText.error =
                getString(R.string.username_already_taken_error_message)
        })
    }

    private fun showDatePicker() {
        MaterialDialog(requireContext()).show {
            datePicker { dialog, date ->
                selectedBirthday = date.also {
                    val dateText = DateFormat.getDateFormat(context).format(it.time)
                    binding.birthdayInputEditText.setText(dateText)
                }
            }
            lifecycleOwner(viewLifecycleOwner)
        }
    }

    private fun showGenderPicker() {
        MaterialDialog(requireContext()).show {
            title(R.string.gender)
            listItemsSingleChoice(R.array.gender) { dialog, index, text ->
                selectedGender = when (text) {
                    "Male" -> Gender.Male
                    "Female" -> Gender.Female
                    else -> Gender.Unknown
                }
                binding.genderInputEditText.setText(text)
            }
            lifecycleOwner(viewLifecycleOwner)
        }
    }

    private fun validateFields() {
        with(binding) {
            if (usernameInputEditText.validator()
                    .minLength(3)
                    .noSpecialCharacters()
                    .addErrorCallback { usernameInputEditText.error = it }.check() &&
                firstNameInputEditText.nonEmpty { firstNameInputEditText.error = it } &&
                lastNameInputEditText.nonEmpty { lastNameInputEditText.error = it } &&
                birthdayInputEditText.nonEmpty { birthdayInputEditText.error = it } &&
                genderInputEditText.nonEmpty { genderInputEditText.error = it } &&
                this@PersonalInfoFragment::selectedBirthday.isInitialized.also {
                    if (it.not()) birthdayInputEditText.error = getString(R.string.error)
                } &&
                this@PersonalInfoFragment::selectedGender.isInitialized.also {
                    if (it.not()) genderInputEditText.error = getString(R.string.error)
                }
            ) {
                PersonalInfo(
                    username = usernameInputEditText.text.toString(),
                    firstName = firstNameInputEditText.text.toString(),
                    lastName = lastNameInputEditText.text.toString(),
                    birthday = selectedBirthday.toLocalDate(),
                    gender = selectedGender
                ).also { viewModel.onPersonalInfoInput(it) }
            }
        }
    }
}
