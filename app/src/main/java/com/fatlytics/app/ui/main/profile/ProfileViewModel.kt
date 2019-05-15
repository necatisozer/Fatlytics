package com.fatlytics.app.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fatlytics.app.domain.entity.DailyActiveness
import com.fatlytics.app.domain.entity.Disease
import com.fatlytics.app.domain.entity.Gender
import com.fatlytics.app.domain.repository.UserRepository
import com.fatlytics.app.extension.EMPTY
import com.fatlytics.app.extension.doInBackground
import com.fatlytics.app.ui.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {
    private val mProfileView = MutableLiveData<ProfileView>()
    val profileView: LiveData<ProfileView> get() = mProfileView

    init {
        userRepository.getCurrentUser()
            .map {
                ProfileView(
                    username = it.personalInfo?.username,
                    info = listOf(
                        ProfileItem(title = "Email", value = it.email),
                        ProfileItem(title = "First Name", value = it.personalInfo?.firstName),
                        ProfileItem(title = "Last Name", value = it.personalInfo?.lastName),
                        ProfileItem(
                            title = "Birthday",
                            value = DateTimeFormatter.ISO_LOCAL_DATE.format(it.personalInfo?.birthday)
                        ),
                        ProfileItem(
                            title = "Gender", value = when (it.personalInfo?.gender) {
                                Gender.MALE -> "Male"
                                Gender.FEMALE -> "Female"
                                else -> String.EMPTY
                            }
                        ),
                        ProfileItem(title = "Height", value = "${it.healthInfo?.height} cm"),
                        ProfileItem(title = "Weight", value = "${it.healthInfo?.weight} kg"),
                        ProfileItem(
                            title = "Daily Activeness",
                            value = when (it.healthInfo?.dailyActiveness) {
                                DailyActiveness.NOT_ACTIVE -> "Not Active"
                                DailyActiveness.LOW -> "Low"
                                DailyActiveness.MEDIUM -> "Medium"
                                DailyActiveness.HIGH -> "High"
                                else -> String.EMPTY
                            }
                        ),
                        ProfileItem(
                            title = "Diseases",
                            value = it.healthInfo?.diseases?.joinToString {
                                when (it) {
                                    Disease.DIABETES -> "Diabetes"
                                    Disease.OBESITY -> "Obesity"
                                    Disease.HEART -> "Heart"
                                }
                            })
                    )
                )
            }
            .doInBackground()
            .subscribeBy(
                onNext = { mProfileView.value = it },
                onError = { mUnexpectedErrorEvent.call() })
            .also { disposables += it }
    }
}

data class ProfileView(
    val username: String?,
    val info: List<ProfileItem>?
)