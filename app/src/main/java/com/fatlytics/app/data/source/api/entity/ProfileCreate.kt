package com.fatlytics.app.data.source.api.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileCreate(
    val profile: Profile?,
    val error: Error?
) : ApiEntity {
    @JsonClass(generateAdapter = true)
    data class Profile(
        val auth_secret: String?,
        val auth_token: String?
    ) : ApiEntity
}