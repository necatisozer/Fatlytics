package com.fatlytics.app.domain.repository

import com.fatlytics.app.domain.entity.User
import io.reactivex.Completable
import io.reactivex.Observable

interface UserRepository : Repository {
    fun checkUserAuth(): Completable
    fun getCurrentUser(): Observable<User>
}