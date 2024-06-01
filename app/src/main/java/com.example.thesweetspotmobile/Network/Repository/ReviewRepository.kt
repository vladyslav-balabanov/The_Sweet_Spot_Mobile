package com.example.thesweetspotmobile.Network.Repository

import com.example.thesweetspotmobile.Network.ApiService
import com.example.thesweetspotmobile.Network.Requests.ReviewRequest
import retrofit2.Response
import javax.inject.Inject

class ReviewRepository @Inject constructor(
    private val apiService: ApiService
){
    suspend fun postReview(token: String, review: ReviewRequest): Response<Unit> {
        return apiService.postReview(token, review)
    }

    suspend fun getReviewById(id: Int): Response<ReviewRequest> {
        return apiService.getReviewById(id)
    }

    suspend fun getReviews(): Response<List<ReviewRequest>> {
        return apiService.getReviews()
    }
}