package com.example.thesweetspotmobile.Network.Responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CartProductResponseModel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "product")
    val product: ProductResponseModel?,
    @Json(name = "productId")
    val productId: Int,
    @Json(name = "cartId")
    val cartId: Int,
    @Json(name = "quantity")
    val quantity: Int
)
