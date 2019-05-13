package com.fatlytics.app.data.repository

import com.fatlytics.app.data.repository.mapper.toHealthInfoFirebaseEntity
import com.fatlytics.app.data.repository.mapper.toPersonalInfoFirebaseEntity
import com.fatlytics.app.data.repository.mapper.toUserEntity
import com.fatlytics.app.data.source.api.FatlyticsApi
import com.fatlytics.app.data.source.api.FatlyticsApiException
import com.fatlytics.app.data.source.firebase.FirebaseManager
import com.fatlytics.app.data.source.firebase.entity.FatlyticsProfile
import com.fatlytics.app.data.source.paper.AuthBook
import com.fatlytics.app.data.source.paper.AuthBookKeys
import com.fatlytics.app.data.source.paper.RegistrationBook
import com.fatlytics.app.data.source.paper.RegistrationBookKeys
import com.fatlytics.app.domain.entity.HealthInfo
import com.fatlytics.app.domain.entity.PersonalInfo
import com.fatlytics.app.domain.entity.User
import com.fatlytics.app.domain.repository.UserRepository
import com.fatlytics.app.helper.Logger
import io.paperdb.Book
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ProdUserRepository @Inject constructor(
    private val fatlyticsApi: FatlyticsApi,
    private val firebaseManager: FirebaseManager,
    @RegistrationBook private val registrationBook: Book,
    @AuthBook private val authBook: Book,
    private val logger: Logger
) : UserRepository {
    override fun checkUserAuth(): Completable =
        getCurrentUser().flatMapCompletable { user ->
            Completable.create { emitter ->
                if (user.banned != true) emitter.onComplete()
                else emitter.onError(UserBannedException())
            }
        }

    override fun getCurrentUser(): Observable<User> =
        firebaseManager.getFirebaseUser().flatMap {
            firebaseManager.getDbUser(it.uid).doOnSuccess {
                it.fatlyticsProfile?.authToken?.let { authBook.write(AuthBookKeys.AUTH_TOKEN, it) }
                it.fatlyticsProfile?.authSecret?.let {
                    authBook.write(
                        AuthBookKeys.AUTH_SECRET,
                        it
                    )
                }
            }.map { it.toUserEntity() }
        }.toObservable()

    override fun validatePersonalInfo(personalInfo: PersonalInfo) = with(personalInfo) {
        registrationBook.write(RegistrationBookKeys.PERSONAL_INFO, this)
        firebaseManager.checkUsername(username!!)
    }

    override fun completeRegistration(healthInfo: HealthInfo) =
        createFatlyticsProfile().flatMapCompletable {
            firebaseManager.createUser(
                personalInfo = registrationBook.read<PersonalInfo?>(RegistrationBookKeys.PERSONAL_INFO)!!.toPersonalInfoFirebaseEntity(),
                healthInfo = healthInfo.toHealthInfoFirebaseEntity(),
                fatlyticsProfile = it
            )
        }

    private fun createFatlyticsProfile() = fatlyticsApi.createProfile().doOnSuccess {
        it.profile?.let {
            it.auth_token?.let { authBook.write(AuthBookKeys.AUTH_TOKEN, it) }
            it.auth_secret?.let { authBook.write(AuthBookKeys.AUTH_SECRET, it) }
        }
        it.error?.let { throw FatlyticsApiException(code = it.code, message = it.message) }
    }.map {
        FatlyticsProfile(
            authSecret = it.profile?.auth_secret,
            authToken = it.profile?.auth_token
        )
    }
}

class UserBannedException : RuntimeException()

