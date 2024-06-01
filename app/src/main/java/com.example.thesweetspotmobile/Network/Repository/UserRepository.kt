package com.example.thesweetspotmobile.Network.Repository

import android.provider.ContactsContract.CommonDataKinds.Phone
import com.example.thesweetspotmobile.Network.ApiService
import com.example.thesweetspotmobile.Network.Responses.UserResponseModel
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService

){
    suspend fun updateUserAvatar(token: String, id: Int, image: MultipartBody.Part): Response<Unit> {
        return apiService.updateUserAvatar(token, id, image)
    }

    suspend fun updateUser(
        token: String,
        id: Int,
        name: String?,
        phone: String?,
        email: String?,
        imageFile: File?
    ): Response<Unit> {

        val idPart = createPartFromString(id.toString())
        val namePart = createPartFromString(name)
        val phonePart = createPartFromString(phone)
        val emailPart = createPartFromString(email)

        val imagePart = imageFile?.let {
            val requestFile = it.readBytes().toRequestBody("image/*".toMediaType())
            MultipartBody.Part.createFormData("image", it.name, requestFile)
        }

        return apiService.updateUser(token, id, idPart, namePart, phonePart, emailPart, imagePart)
    }

    private fun createPartFromString(name: String?): RequestBody {
        return name?.toRequestBody("text/plain".toMediaType()) ?: "".toRequestBody("text/plain".toMediaType())
    }


    suspend fun getUserById(token: String, id: Int): Response<UserResponseModel> {
        return apiService.getUserById(token, id)
    }
}