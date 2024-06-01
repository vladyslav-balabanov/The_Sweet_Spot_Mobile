package com.example.thesweetspotmobile.Network.Responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewResponseModel(
    val id: Int,
    val text: String,
    val grade: Int,
    val product: ProductResponseModel,
    val productId: Int,
    val user: UserResponseModel,
    val userId: Int
)
