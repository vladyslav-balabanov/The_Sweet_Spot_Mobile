package com.example.thesweetspotmobile.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.thesweetspotmobile.Network.Repository.AuthRepository
import com.example.thesweetspotmobile.Network.Responses.AuthResponseModel
import com.example.thesweetspotmobile.Utils.TokenManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _isAuthSuccessed = MutableLiveData<Boolean>(false)
    val isAuthSuccessed: LiveData<Boolean> get() = _isAuthSuccessed


    fun googleAuth(account: GoogleSignInAccount?) {
        viewModelScope.launch {
            val email = account?.email ?: ""
            val name = account?.displayName ?: ""
            googleSignIn(email, name)
        }
    }
    private suspend fun googleSignIn(email: String, name: String) {
        val authResponse: Response<AuthResponseModel> = repository.registerUserWithGoogle(email, name)
        if (authResponse.isSuccessful) {
            val responseAuthBody = authResponse.body()
            if (responseAuthBody?.successed == true) {
                responseAuthBody.jwtToken?.let { tokenManager.saveToken(it) }
                _isAuthSuccessed.value = true
            } else if (responseAuthBody?.errorString == "this email is already taken") {
                googleSignUp(email)
            }
        }
    }

    private suspend fun googleSignUp(email: String) {
        val authResponse: Response<AuthResponseModel> = repository.loginUser(email, null, 1)
        if (authResponse.isSuccessful) {
            _isAuthSuccessed.value = true
            authResponse.body()?.jwtToken?.let { tokenManager.saveToken(it) }
        }
    }

    fun defaultSignIn(email: String, password: String) {
        viewModelScope.launch {
            val authResponse: Response<AuthResponseModel> = repository.loginUser(email, password, 0)
            if (authResponse.isSuccessful) {
                _isAuthSuccessed.value = true
                authResponse.body()?.jwtToken?.let { tokenManager.saveToken(it) }
            }
        }
    }

    fun defaultSignUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            val authResponse: Response<AuthResponseModel> = repository.registerUser(name, email, password)
            if (authResponse.isSuccessful) {
                _isAuthSuccessed.value = true
                authResponse.body()?.jwtToken?.let { tokenManager.saveToken(it) }
            }
        }
    }

    fun resetAuthState() {
        _isAuthSuccessed.value = false
    }


}