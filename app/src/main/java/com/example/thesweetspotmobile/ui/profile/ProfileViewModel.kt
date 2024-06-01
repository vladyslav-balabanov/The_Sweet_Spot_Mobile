package com.example.thesweetspotmobile.ui.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thesweetspotmobile.Network.Repository.UserRepository
import com.example.thesweetspotmobile.Network.Responses.UserResponseModel
import com.example.thesweetspotmobile.Utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @ApplicationContext private val appContext: Context,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _user = MutableLiveData<UserResponseModel>()
    val user: LiveData<UserResponseModel> get() = _user

    init {
        fetchUserProfile()
    }

    fun fetchUserProfile() {
        tokenManager.fetchToken()?.let { token ->
            tokenManager.getUserId(token)?.toIntOrNull()?.let { id ->
                viewModelScope.launch {
                    userRepository.getUserById("Bearer $token", id).body()?.let {
                        _user.postValue(it)
                    }
                }
            }
        }
    }

    fun updateUserProfile(
        imageFile: File?,
        name: String?,
        phone: String?,
        email: String?
    ) {
        tokenManager.fetchToken()?.let { token ->
            _user.value?.id?.let { id ->
                viewModelScope.launch {
                    val response = userRepository.updateUser(
                        "Bearer $token", id, name, phone, email, imageFile
                    )
                    if (response.isSuccessful) {
                        fetchUserProfile()
                    }
                }
            }
        }
    }

    fun decodeImage(uri: Uri): Bitmap? {
        return appContext.contentResolver.openInputStream(uri)?.let { BitmapFactory.decodeStream(it) }
    }
}