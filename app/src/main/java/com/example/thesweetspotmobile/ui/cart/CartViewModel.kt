package com.example.thesweetspotmobile.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thesweetspotmobile.Network.Repository.CartRepository
import com.example.thesweetspotmobile.Network.Requests.CartRequest
import com.example.thesweetspotmobile.Network.Responses.CartResponseModel
import com.example.thesweetspotmobile.Utils.TokenManager
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository,
    private val tokenManager: TokenManager,
    private val moshi: Moshi
): ViewModel() {

    private val _cart = MutableLiveData<CartResponseModel?>()
    val cart: MutableLiveData<CartResponseModel?> get() = _cart

    init {
        loadCart()
    }

    fun loadCart() {
        viewModelScope.launch {
            val token = tokenManager.fetchToken()
            val response = token?.let { repository.getCarts("Bearer $it") }
            if (response?.isSuccessful == true) {
                response.body()?.let { carts ->
                    _cart.value = carts
                }
            }
        }
    }

    fun updateCartProduct(request: CartRequest) {
        viewModelScope.launch {
            val token = tokenManager.fetchToken()
            val response = token?.let { repository.updateCartProduct("Bearer $it", request) }
            if (response?.isSuccessful == true) {
                val updatedCart = _cart.value?.cartProducts?.map {
                    if (it.productId == request.productId) {
                        it.copy(quantity = request.quantity)
                    } else {
                        it
                    }
                }?.filter { it.quantity > 0 }
                _cart.value = _cart.value?.copy(cartProducts = updatedCart ?: listOf())
            }
        }
    }

    fun removeProduct(productId: Int) {
        val token = tokenManager.fetchToken()
        if (token != null) {
            updateCartProduct(CartRequest(productId = productId, quantity = 0))
        }
    }

    fun deleteCart() {
        val token = tokenManager.fetchToken()
        if (token != null) {
            viewModelScope.launch {
                val response = repository.deleteCart("Bearer $token")
                if (response.isSuccessful) {
                    _cart.value = null
                }
            }
        }
    }
}