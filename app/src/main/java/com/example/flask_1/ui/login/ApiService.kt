package com.example.flask_1.ui.login

import com.example.flask_1.ui.gallery.Users
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {
    @POST("/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/signup")
    fun signup(@Body request: SignUpRequest): Call<SignUpResponse>

    @POST("/upload_image")
    fun uploadImage(@Body base64Image: RequestBody): Call<OcrResponse>

    @POST("/save_exam")
    fun saveExam(@Body examData: ExamData): Call<SaveExamResponse>

    @POST("/exams")
    fun getExams(@Body username: Map<String, String>): Call<List<Exam>>

    @POST("/get_my_exams")
    fun getMyExams(@Body params: Map<String, String>): Call<List<Exam>>

}



