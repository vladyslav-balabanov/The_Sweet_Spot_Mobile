package com.example.thesweetspotmobile.Network.Repository

import com.example.thesweetspotmobile.Network.ApiService
import com.example.thesweetspotmobile.Network.Responses.ProductResponseModel
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val apiService: ApiService
){
    suspend fun getProducts(category: Int? = null, sort: Int? = null, query: String? = null): Response<List<ProductResponseModel>> {
        return apiService.getProducts(category, sort, query)
    }

    suspend fun getProductById(id: Int): ProductResponseModel {
        return apiService.getProductById(id)
    }
}