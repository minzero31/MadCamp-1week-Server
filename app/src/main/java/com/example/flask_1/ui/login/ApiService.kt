package com.example.flask_1

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST("/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/signup")
    fun signup(@Body request: SignUpRequest): Call<SignUpResponse>

    @POST("/upload_image")
    fun uploadImage(@Body base64Image: RequestBody): Call<OcrResponse>
}
