package com.example.thesweetspotmobile.Network.Responses

import com.example.thesweetspotmobile.Network.Responses.CartResponseModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderResponseModel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "createdDate")
    val createdDate: String,
    @Json(name = "updatedDate")
    val updatedDate: String,
    @Json(name = "cart")
    val cart: CartResponseModel,
    @Json(name = "cartId")
    val cartId: Int,
    @Json(name = "status")
    val status: Int,
    @Json(name = "area")
    val area: String,
    @Json(name = "street")
    val street: String,
    @Json(name = "houseNumber")
    val houseNumber: String
)

