package com.example.sweetspot.Network.Responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponseModel(
    @Json(name = "successed")
    val successed: Boolean,
    @Json(name = "jwtToken")
    val jwtToken: String?,
    @Json(name = "errorString")
    val errorString: String?
)
