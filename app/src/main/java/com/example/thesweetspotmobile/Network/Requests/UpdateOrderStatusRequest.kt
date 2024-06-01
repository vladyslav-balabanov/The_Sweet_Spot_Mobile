package com.example.thesweetspotmobile.Network.Requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateOrderStatusRequest(
    @Json(name = "orderId")
    val orderId: Int,
    @Json(name = "status")
    val status: Int
)
