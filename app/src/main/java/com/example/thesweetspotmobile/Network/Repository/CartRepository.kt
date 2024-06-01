package com.example.thesweetspotmobile.Network.Repository

import com.example.thesweetspotmobile.Network.ApiService
import com.example.thesweetspotmobile.Network.Requests.CartRequest
import com.example.thesweetspotmobile.Network.Responses.CartResponseModel
import retrofit2.Response
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val apiService: ApiService
){
    suspend fun getCarts(token: String): Response<CartResponseModel> {
        return apiService.getCarts(token)
    }

    suspend fun addToCart(token: String, request: CartRequest): Response<Unit> {
        return apiService.addToCart(token, request)
    }

    suspend fun updateCartProduct(token: String, request: CartRequest): Response<Unit> {
        return apiService.updateCartProduct(token, request)
    }

    suspend fun deleteCart(token: String): Response<Unit> {
        return apiService.deleteCart(token)
    }
}