package com.example.thesweetspotmobile.ui.historydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thesweetspotmobile.Network.Repository.OrderRepository
import com.example.thesweetspotmobile.Network.Repository.ReviewRepository
import com.example.thesweetspotmobile.Network.Requests.ReviewRequest
import com.example.thesweetspotmobile.Network.Responses.OrderResponseModel
import com.example.thesweetspotmobile.Utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryDetailsViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val reviewRepository: ReviewRepository,
    private val tokenManager: TokenManager
) : ViewModel() {
    private val _orderDetails = MutableLiveData<OrderResponseModel>()
    val orderDetails: LiveData<OrderResponseModel> = _orderDetails

    fun loadOrderDetails(orderId: String) {
        viewModelScope.launch {
            val token = tokenManager.fetchToken()
            val details = orderRepository.getOrdersById("Bearer $token", orderId)
            if (details.isSuccessful && details.body() != null) _orderDetails.value = details.body()
        }
    }

    fun postReview(productId: Int, grade: Int, text: String) {
        viewModelScope.launch {
            try {
                val token = tokenManager.fetchToken()
                val review = ReviewRequest(text = text, grade = grade, productId = productId)
                reviewRepository.postReview("Bearer $token", review)
            } catch (e: Exception) {
                print(e)
            }
        }
    }
}