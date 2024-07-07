package com.example.flask_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        val signupButton = findViewById<Button>(R.id.signupButton)

        signupButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()) {
                if (password == confirmPassword) {
                    val signupRequest = SignUpRequest(username, email, password)
                    RetrofitClient.apiService.signup(signupRequest).enqueue(object : Callback<SignUpResponse> {
                        override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                            if (response.isSuccessful) {
                                val signupResponse = response.body()
                                if (signupResponse != null) {
                                    Toast.makeText(this@SignUpActivity, signupResponse.message, Toast.LENGTH_LONG).show()
                                    navigateToLoginActivity()
                                }
                            } else {
                                Toast.makeText(this@SignUpActivity, "Signup failed: ${response.message()}", Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                            Toast.makeText(this@SignUpActivity, "Signup failed: ${t.message}", Toast.LENGTH_LONG).show()
                        }
                    })
                } else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Please enter username, email, and both password fields", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()  // Optionally finish SignUpActivity to prevent going back to it
    }
}
