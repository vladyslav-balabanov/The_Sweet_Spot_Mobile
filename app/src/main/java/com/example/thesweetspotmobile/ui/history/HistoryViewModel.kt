package com.example.thesweetspotmobile.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.thesweetspotmobile.Network.Repository.OrderRepository
import com.example.thesweetspotmobile.Network.Responses.CartProductResponseModel
import com.example.thesweetspotmobile.Network.Responses.CartResponseModel
import com.example.thesweetspotmobile.Network.Responses.OrderResponseModel
import com.example.thesweetspotmobile.Network.Responses.ProductResponseModel
import com.example.thesweetspotmobile.Network.Responses.UserResponseModel
import com.example.thesweetspotmobile.Utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val orderHistoryRepository: OrderRepository,
    private val tokenManager: TokenManager
) : ViewModel() {
    private val _orders = MutableLiveData<List<OrderResponseModel>>()
    val orders: LiveData<List<OrderResponseModel>> get() = _orders

    init {
        loadOrders()
    }

    private fun loadOrders() {
        viewModelScope.launch {
            val token = tokenManager.fetchToken()
            val response = token?.let { orderHistoryRepository.getOrders("Bearer $it") }
            if (response?.isSuccessful == true && response?.body() != null) {
                _orders.value = response.body()
            }
        }
    }
}