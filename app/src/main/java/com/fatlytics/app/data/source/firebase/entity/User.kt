package com.fatlytics.app.data.source.firebase.entity

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class User(
    val banned: Boolean? = null,
    val birthday: String? = null,
    @ServerTimestamp val created_at: Date? = null,
    val daily_activeness: DocumentReference? = null,
    val diseases: List<DocumentReference>? = null,
    val email: String? = null,
    val first_name: String? = null,
    val gender: DocumentReference? = null,
    val last_name: String? = null,
    val token: String? = null,
    val uid: String? = null,
    @ServerTimestamp val updated_at: Date? = null,
    val username: String? = null
) : FirebaseEntity
