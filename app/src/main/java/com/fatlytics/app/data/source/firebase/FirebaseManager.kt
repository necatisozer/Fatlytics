package com.fatlytics.app.data.source.firebase

import com.fatlytics.app.data.source.firebase.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class FirebaseManager @Inject constructor() {
    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    fun getFirebaseUser() = Single.create<FirebaseUser> { emitter ->
        auth.currentUser?.let { emitter.onSuccess(it) }
            ?: emitter.onError(UserNotSignedInException())
    }

    fun getDbUser(uid: String) = Single.create<User> { emitter ->
        db.collection("user")
            .whereEqualTo("uid", uid)
            .get()
            .addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    val user = it.documents[0].toObject(User::class.java)
                    user?.let { emitter.onSuccess(it) } ?: emitter.onError(DeserializeException())
                } else {
                    emitter.onError(UserNotFoundException())
                }
            }.addOnFailureListener { emitter.onError(it) }
    }

    fun checkUsername(username: String) = Completable.create { emitter ->
        db.collection("user")
            .whereEqualTo("username", username)
            .get()
            .addOnSuccessListener {
                if (it.documents.isEmpty()) emitter.onComplete()
                else emitter.onError(UsernameAlreadyTakenException())
            }
            .addOnFailureListener { emitter.onError(it) }
    }
}

abstract class FirebaseException : RuntimeException()
class UserNotSignedInException : FirebaseException()
class UserNotFoundException : FirebaseException()
class DeserializeException : FirebaseException()
class UsernameAlreadyTakenException : FirebaseException()