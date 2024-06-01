package com.example.thesweetspotmobile.Network.Repository

import com.example.thesweetspotmobile.Network.ApiService
import com.example.thesweetspotmobile.Network.Requests.OrderRequest
import com.example.thesweetspotmobile.Network.Requests.UpdateOrderStatusRequest
import com.example.thesweetspotmobile.Network.Responses.OrderResponseModel
import retrofit2.Response
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val apiService: ApiService
){
    suspend fun getOrders(token: String): Response<List<OrderResponseModel>> {
        return apiService.getOrders(token)
    }

    suspend fun getOrdersById(token: String, id: String): Response<OrderResponseModel> {
        return apiService.getOrdersById(token, id)
    }

    suspend fun createOrder(token: String, request: OrderRequest): Response<OrderResponseModel> {
        return apiService.createOrder(token, request)
    }

    suspend fun updateOrderStatus(token: String, request: UpdateOrderStatusRequest): Response<Unit> {
        return apiService.updateOrderStatus(token, request)
    }
}