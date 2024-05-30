package com.example.thesweetspotmobile.Network.Responses

import androidx.compose.runtime.Stable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Stable
@JsonClass(generateAdapter = true)
data class ProductResponseModel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "cost")
    val cost: Double,
    @Json(name = "image")
    val image: String,
    @Json(name = "category")
    val category: Int
)