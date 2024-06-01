package com.example.thesweetspotmobile.Network

import com.example.thesweetspotmobile.Network.Requests.OAuthRegisterModel
import com.example.thesweetspotmobile.Network.Requests.LoginModel
import com.example.thesweetspotmobile.Network.Requests.OrderRequest
import com.example.thesweetspotmobile.Network.Requests.RegisterModel
import com.example.thesweetspotmobile.Network.Requests.ReviewRequest
import com.example.thesweetspotmobile.Network.Requests.UpdateOrderStatusRequest
import com.example.thesweetspotmobile.Network.Responses.AuthResponseModel
import com.example.thesweetspotmobile.Network.Responses.CartResponseModel
import com.example.thesweetspotmobile.Network.Responses.OrderResponseModel
import com.example.thesweetspotmobile.Network.Responses.ProductResponseModel
import com.example.thesweetspotmobile.Network.Responses.UserResponseModel
import com.example.thesweetspotmobile.Network.Requests.CartRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("api/auth/login")
    suspend fun loginUser(@Body loginModel: LoginModel): Response<AuthResponseModel>

    @POST("api/auth/reg/user")
    suspend fun registerUser(@Body registerModel: RegisterModel): Response<AuthResponseModel>

    @POST("api/auth/reg/user/google")
    suspend fun registerUserWithGoogle(@Body googleRegisterModel: OAuthRegisterModel): Response<AuthResponseModel>

    @POST("api/auth/reg/user/facebook")
    suspend fun registerUserWithFacebook(@Body facebookRegisterModel: OAuthRegisterModel): Response<AuthResponseModel>

    @GET("/api/Products")
    suspend fun getProducts(
        @Query("category") category: Int? = null,
        @Query("sort") sort: Int? = null,
        @Query("searchQuery") query: String? = null
    ): Response<List<ProductResponseModel>>

    @GET("/api/Products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductResponseModel

    @Multipart
    @PUT("/api/Users/{id}")
    suspend fun updateUserAvatar(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part image: MultipartBody.Part
    ): Response<Unit>

    @Multipart
    @PUT("/api/Users/{id}")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part("Id") userId: RequestBody,
        @Part("Name") name: RequestBody,
        @Part("Phone") phone: RequestBody,
        @Part("Email") email: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<Unit>

    @GET("/api/Users/{id}")
    suspend fun getUserById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<UserResponseModel>

    @POST("/api/Reviews")
    suspend fun postReview(
        @Header("Authorization") token: String,
        @Body review: ReviewRequest
    ): Response<Unit>

    @GET("/api/Reviews/{id}")
    suspend fun getReviewById(
        @Path("id") id: Int
    ): Response<ReviewRequest>

    @GET("/api/Reviews")
    suspend fun getReviews(): Response<List<ReviewRequest>>

    @GET("/api/Carts")
    suspend fun getCarts(
        @Header("Authorization") token: String
    ): Response<CartResponseModel>

    @DELETE("/api/Carts")
    suspend fun deleteCart(
        @Header("Authorization") token: String
    ): Response<Unit>

    @POST("/api/Carts")
    suspend fun addToCart(
        @Header("Authorization") token: String,
        @Body request: CartRequest
    ): Response<Unit>

    @PATCH("/api/Carts")
    suspend fun updateCartProduct(
        @Header("Authorization") token: String,
        @Body request: CartRequest
    ): Response<Unit>

    @GET("/api/Orders/user")
    suspend fun getOrders(
        @Header("Authorization") token: String
    ): Response<List<OrderResponseModel>>

    @POST("/api/Orders")
    suspend fun createOrder(
        @Header("Authorization") token: String,
        @Body request: OrderRequest
    ): Response<OrderResponseModel>

    @PATCH("/api/Orders")
    suspend fun updateOrderStatus(
        @Header("Authorization") token: String,
        @Body request: UpdateOrderStatusRequest
    ): Response<Unit>

    @GET("/api/Orders/{id}")
    suspend fun getOrdersById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<OrderResponseModel>




    companion object {
        const val BASE_URL = "http://thesweetspot-001-site1.htempurl.com"
    }
}