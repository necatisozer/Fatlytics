package com.fatlytics.app.extension.firebase

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

/**
 * Add an action which will be invoked when a new child is added to the location to which this listener was added.
 */
fun DatabaseReference.doOnChildAdded(action: (dataSnapShot: DataSnapshot?) -> Unit) =
    addChildListener(onChildAdded = action)

/**
 * Add an action which will be invoked when a child is removed from the location to which this listener was added.
 */
fun DatabaseReference.doOnChildRemoved(action: (dataSnapShot: DataSnapshot?) -> Unit) =
    addChildListener(onChildRemoved = action)

/**
 *  Add an action which will be invoked when a child location's priority changes.
 */
fun DatabaseReference.doOnChildMoved(action: (dataSnapShot: DataSnapshot?) -> Unit) =
    addChildListener(onChildMoved = action)

/**
 * Add an action which will be invoked when the data at a child location has changed.
 */
fun DatabaseReference.doOnChildChanged(action: (dataSnapShot: DataSnapshot?) -> Unit) =
    addChildListener(onChildChanged = action)

/**
 * Add an action which will be invoked when this listener either failed at the server, or is removed as a result of the security and Firebase rules.
 */
fun DatabaseReference.doOnCanceled(action: (databaseError: DatabaseError?) -> Unit) =
    addChildListener(onCanceled = action)

/**
 * Add a listener to the child reference with the given actions
 */
fun DatabaseReference.addChildListener(
    onCanceled: ((databaseError: DatabaseError?) -> Unit)? = null,
    onChildMoved: ((dataSnapshot: DataSnapshot?) -> Unit)? = null,
    onChildChanged: ((dataSnapshot: DataSnapshot?) -> Unit)? = null,
    onChildAdded: ((dataSnapshot: DataSnapshot?) -> Unit)? = null,
    onChildRemoved: ((dataSnapshot: DataSnapshot?) -> Unit)? = null
): DatabaseReference {
    val listener = object : ChildEventListener {
        override fun onCancelled(databaseError: DatabaseError) {
            onCanceled?.invoke(databaseError)
        }

        override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
            onChildMoved?.invoke(dataSnapshot)
        }

        override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
            onChildChanged?.invoke(dataSnapshot)
        }

        override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
            onChildAdded?.invoke(dataSnapshot)
        }

        override fun onChildRemoved(dataSnapshot: DataSnapshot) {
            onChildRemoved?.invoke(dataSnapshot)
        }
    }
    addChildEventListener(listener)
    return this
}