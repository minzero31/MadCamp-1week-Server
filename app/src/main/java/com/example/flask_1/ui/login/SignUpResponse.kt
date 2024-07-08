package com.example.flask_1

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val message: String)
data class SignUpRequest(val username: String, val email: String, val password: String)
data class SignUpResponse(val message: String)
data class OcrResponse(val ocrText: String, val questions: List<String>, val options: List<List<String>>, val answers: List<Int>)
