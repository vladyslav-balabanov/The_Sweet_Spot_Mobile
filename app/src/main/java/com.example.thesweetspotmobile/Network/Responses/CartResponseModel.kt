package com.example.thesweetspotmobile.Network.Responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CartResponseModel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "user")
    val user: UserResponseModel?,
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "isActive")
    val isActive: Boolean,
    @Json(name = "cartProducts")
    val cartProducts: List<CartProductResponseModel>?
)