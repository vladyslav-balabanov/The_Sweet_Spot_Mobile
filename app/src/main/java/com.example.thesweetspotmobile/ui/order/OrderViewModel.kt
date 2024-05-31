package com.example.thesweetspotmobile.ui.order

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sweetspot.Network.Repository.OrderRepository
import com.example.sweetspot.Network.Requests.OrderRequest
import com.example.sweetspot.Network.Responses.CartResponseModel
import com.example.sweetspot.Utils.TokenManager
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    var area: String by mutableStateOf("")
    var street: String by mutableStateOf("")
    var houseNumber: String by mutableStateOf("")


    fun submitOrder(cartId: Int?) {
        if (cartId == null) return
        val token = tokenManager.fetchToken()
        if (token != null) {
            viewModelScope.launch {
                val orderRequest = OrderRequest(
                    cartId = cartId,
                    status = 1,
                    area = area,
                    street = street,
                    houseNumber = houseNumber
                )
                orderRepository.createOrder("Bearer $token", orderRequest)
            }
        }
    }
}