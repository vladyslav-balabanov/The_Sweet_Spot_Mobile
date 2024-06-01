package com.example.thesweetspotmobile.Network.Requests
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderRequest(
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