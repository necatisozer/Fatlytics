package com.fatlytics.app.data.source.firebase

import com.fatlytics.app.data.source.firebase.entity.User
import com.fatlytics.app.extension.firebase.observeChildren
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class FirebaseManager @Inject constructor() {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference

    fun getFirebaseUser() = Single.create<FirebaseUser> { emitter ->
        auth.currentUser?.let { emitter.onSuccess(it) }
            ?: emitter.onError(UserNotSignedInException())
    }

    fun getDbUser(uid: String) = Single.create<User> { emitter ->
        database.child("users").orderByChild("uid").equalTo(uid)
            .observeChildren<User> { users, error ->
                users?.let {
                    if (users.isNotEmpty()) emitter.onSuccess(users[0])
                    else emitter.onError(UserNotFoundException())
                }
                error?.let { emitter.onError(it.toException()) }
            }
    }

    fun checkUsername(username: String) = Completable.create { emitter ->
        database.child("users").orderByKey().equalTo(username)
            .observeChildren<User> { users, error ->
                users?.let {
                    if (users.isEmpty()) emitter.onComplete()
                    else emitter.onError(UsernameAlreadyTakenException())
                }
                error?.let { emitter.onError(it.toException()) }
            }
    }
}

abstract class FirebaseException : RuntimeException()
class UserNotSignedInException : FirebaseException()
class UserNotFoundException : FirebaseException()
class UsernameAlreadyTakenException : FirebaseException()