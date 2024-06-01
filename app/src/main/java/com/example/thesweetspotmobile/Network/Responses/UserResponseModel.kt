package com.example.thesweetspotmobile.Network.Responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponseModel(
    @Json(name = "id") val id: Int,
    @Json(name = "email") val email: String?,
    @Json(name = "password") val password: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "role") val role: String?,
    @Json(name = "authMethod") val authMethod: Int?,
    @Json(name = "phone") val phone: String?,
    @Json(name = "image") val image: String?
)
