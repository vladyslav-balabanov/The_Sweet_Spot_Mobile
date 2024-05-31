package com.example.thesweetspotmobile.Network.Requests
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewRequest(
    @Json(name = "text")
    val text: String,
    @Json(name = "grade")
    val grade: Int,
    @Json(name = "productId")
    val productId: Int
)
