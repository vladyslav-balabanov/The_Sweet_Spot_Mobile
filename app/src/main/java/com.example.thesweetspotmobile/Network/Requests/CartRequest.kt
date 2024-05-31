package com.example.thesweetspotmobile.Network.Requests

import com.squareup.moshi.Json


data class CartRequest(
    @Json(name = "productId")
    val productId: Int,
    @Json(name = "quantity")
    val quantity: Int
)