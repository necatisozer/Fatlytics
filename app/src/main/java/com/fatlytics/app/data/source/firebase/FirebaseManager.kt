package com.fatlytics.app.data.source.firebase

import com.fatlytics.app.data.source.firebase.entity.FatlyticsProfile
import com.fatlytics.app.data.source.firebase.entity.HealthInfo
import com.fatlytics.app.data.source.firebase.entity.PersonalInfo
import com.fatlytics.app.data.source.firebase.entity.User
import com.fatlytics.app.extension.firebase.observeSingleChildrenEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseManager @Inject constructor() {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference

    fun getFirebaseUser() = Single.create<FirebaseUser> { emitter ->
        auth.currentUser?.let { emitter.onSuccess(it) }
            ?: emitter.onError(UserNotSignedInException())
    }

    fun getDbUser(uid: String) = Single.create<User> { emitter ->
        database.child("users").orderByChild("uid").equalTo(uid)
            .observeSingleChildrenEvent<User> { users, error ->
                users?.let {
                    if (users.isNotEmpty()) emitter.onSuccess(users[0])
                    else emitter.onError(UserNotFoundException())
                }
                error?.let { emitter.onError(it.toException()) }
            }
    }

    fun checkUsername(username: String) = Completable.create { emitter ->
        database.child("users").orderByKey().equalTo(username)
            .observeSingleChildrenEvent<User> { users, error ->
                users?.let {
                    if (users.isEmpty()) emitter.onComplete()
                    else emitter.onError(UsernameAlreadyTakenException())
                }
                error?.let { emitter.onError(it.toException()) }
            }
    }

    fun createUser(
        personalInfo: PersonalInfo,
        healthInfo: HealthInfo,
        fatlyticsProfile: FatlyticsProfile
    ) =
        Completable.create { emitter ->
            val username = personalInfo.username?.let { it } ?: error("Username is required")
            val user = User(
                banned = false,
                createdAt = ServerValue.TIMESTAMP,
                email = auth.currentUser?.email,
                fatlyticsProfile = fatlyticsProfile,
                healthInfo = healthInfo,
                personalInfo = personalInfo,
                uid = auth.currentUser?.uid,
                updatedAt = ServerValue.TIMESTAMP
            )
            database.child("users").child(username).setValue(user) { error, ref ->
                error?.let { emitter.onError(it.toException()) } ?: emitter.onComplete()
            }
        }
}

abstract class FirebaseException : RuntimeException()
class UserNotSignedInException : FirebaseException()
class UserNotFoundException : FirebaseException()
class UsernameAlreadyTakenException : FirebaseException()