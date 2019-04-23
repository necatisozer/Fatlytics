package com.fatlytics.app.data.repository

import com.fatlytics.app.data.repository.mapper.toUserEntity
import com.fatlytics.app.data.source.firebase.FirebaseManager
import com.fatlytics.app.domain.entity.PersonalInfo
import com.fatlytics.app.domain.entity.User
import com.fatlytics.app.domain.repository.UserRepository
import com.fatlytics.app.helper.Logger
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ProdUserRepository @Inject constructor(
    private val firebaseManager: FirebaseManager,
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
            firebaseManager.getDbUser(it.uid).map { it.toUserEntity() }
        }.toObservable()

    override fun validatePersonalInfo(personalInfo: PersonalInfo) =
        firebaseManager.checkUsername(personalInfo.username!!)
}

class UserBannedException : RuntimeException()