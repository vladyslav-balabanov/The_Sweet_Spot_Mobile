package com.example.thesweetspotmobile.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thesweetspotmobile.Network.Repository.CartRepository
import com.example.thesweetspotmobile.Network.Repository.ProductRepository
import com.example.thesweetspotmobile.Network.Requests.CartRequest
import com.example.thesweetspotmobile.Network.Responses.CartResponseModel
import com.example.thesweetspotmobile.Network.Responses.ProductResponseModel
import com.example.thesweetspotmobile.Utils.TokenManager

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val repository: CartRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _products = MutableLiveData<List<ProductResponseModel>>()
    val products: LiveData<List<ProductResponseModel>> get() = _products

    private val _cart = MutableLiveData<CartResponseModel>()
    val cart: LiveData<CartResponseModel> get() = _cart

    private val _filterCategory = MutableLiveData<Int?>(null)
    private val _sortOrder = MutableLiveData<Int?>(null)
    private val _querySearch = MutableLiveData<String?>(null)


    init {
        loadProducts()
        loadCart()
    }

    fun onSortChanged(option: String) {
        val newSortOrder = when (option) {
            "За назвою" -> 0
            "За спаданням ціни" -> 1
            "За зростанням ціни" -> 2
            else -> null
        }
        if (newSortOrder != _sortOrder.value) {
            _sortOrder.value = newSortOrder
            loadProducts()
        }
    }

    fun onCategoryChanged(categoryIndex: Int) {
        val newCategory = if (categoryIndex >= 0) categoryIndex else null
        if (newCategory != _filterCategory.value) {
            _filterCategory.value = newCategory
            loadProducts()
        }
    }

    fun onSearchQueryChanged(query: String) {
        if (query != _querySearch.value) {
            _querySearch.value = query
            loadProducts()
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            val response = productRepository.getProducts(_filterCategory.value, _sortOrder.value, _querySearch.value)
            if (response.isSuccessful) {
                response.body()?.let { products ->
                    _products.value = products
                }
            }
        }
    }

    fun loadCart() {
        val token = tokenManager.fetchToken()
        if (token != null) {
            viewModelScope.launch {
                val response = repository.getCarts(token)
                if (response.isSuccessful) {
                    response.body()?.let { carts ->
                        _cart.value = carts
                    }
                }
            }
        }
    }

    fun addToCart(productId: Int, quantity: Int) {
        val token = tokenManager.fetchToken()
        if (token != null) {
            viewModelScope.launch {
                if (!isProductInCart(productId)) {
                    val request = CartRequest(productId = productId, quantity = quantity)
                    val response = repository.addToCart("Bearer $token", request)
                    if (response.isSuccessful && response.body() == null) {
                        loadCart()
                    }
                }
            }
        }
    }

    private fun isProductInCart(productId: Int): Boolean {
        return _cart.value?.cartProducts?.any { it.productId == productId } == true
    }
}