package com.example.thesweetspotmobile.Network.Repository

import com.example.thesweetspotmobile.Network.ApiService
import com.example.thesweetspotmobile.Network.Requests.LoginModel
import com.example.thesweetspotmobile.Network.Requests.OAuthRegisterModel
import com.example.thesweetspotmobile.Network.Requests.RegisterModel
import com.example.thesweetspotmobile.Network.Responses.AuthResponseModel
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService
){
    suspend fun loginUser(email: String, password: String? = null, authMethod: Int): Response<AuthResponseModel> {
        return apiService.loginUser(LoginModel(email, password, authMethod))
    }

    suspend fun registerUser(name: String, email: String, password: String): Response<AuthResponseModel> {
        return apiService.registerUser(RegisterModel(name, email, password))
    }

    suspend fun registerUserWithGoogle(email: String, name: String): Response<AuthResponseModel> {
        return apiService.registerUserWithGoogle(OAuthRegisterModel(email, name))
    }

    suspend fun registerUserWithFacebook(email: String, name: String): Response<AuthResponseModel> {
        return apiService.registerUserWithFacebook(OAuthRegisterModel(email, name))
    }
}