package com.example.flask_1.ui.login

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val message: String)
data class SignUpRequest(val username: String, val email: String, val password: String)
data class SignUpResponse(val message: String)
data class OcrResponse(val ocrText: String, val questions: List<String>, val options: List<List<String>>, val answers: List<String>)
data class ExamData(val username: String, val problems: List<ProblemData>)
data class ProblemData(
    val question: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val option5: String,
    val answer: String
)

data class SaveExamResponse(val message: String)
