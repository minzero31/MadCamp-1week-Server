package com.example.flask_1.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flask_1.MainActivity
import com.example.flask_1.R
import com.example.flask_1.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var isPasswordVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usernameEditText = binding.usernameEditText
        val passwordEditText = binding.passwordEditText
        val rememberMeCheckBox = binding.rememberMeCheckBox
        val passwordToggle = binding.passwordToggle
        val loginButton = binding.loginButton
        val signupButton = binding.signupButton

        // Check if user credentials are saved
        checkSavedCredentials()

        passwordToggle.setOnClickListener {
            togglePasswordVisibility()
        }

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val rememberMe = rememberMeCheckBox.isChecked

            if (username.isNotBlank() && password.isNotBlank()) {
                val loginRequest = LoginRequest(username, password)
                RetrofitClient.apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful) {
                            val loginResponse = response.body()
                            if (loginResponse != null) {
                                Toast.makeText(this@LoginActivity, loginResponse.message, Toast.LENGTH_LONG).show()
                                if (rememberMe) {
                                    saveCredentials(username, password)
                                }
                                navigateToHomeActivity()
                            }
                        } else {
                            Toast.makeText(this@LoginActivity, "Login failed: ${response.message()}", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Login failed: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
            } else {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_LONG).show()
            }
        }

        signupButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkSavedCredentials() {
        val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("username", null)
        val savedPassword = sharedPreferences.getString("password", null)
        if (savedUsername != null && savedPassword != null) {
            binding.usernameEditText.setText(savedUsername)
            binding.passwordEditText.setText(savedPassword)
            binding.rememberMeCheckBox.isChecked = true
            // Automatically log in the user
            val loginRequest = LoginRequest(savedUsername, savedPassword)
            RetrofitClient.apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse != null) {
                            Toast.makeText(this@LoginActivity, loginResponse.message, Toast.LENGTH_LONG).show()
                            navigateToHomeActivity()
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Login failed: ${response.message()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Login failed: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun saveCredentials(username: String, password: String) {
        val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("username", username)
            putString("password", password)
            apply()
        }
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            binding.passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.passwordToggle.setImageResource(R.drawable.ic_eye_off) // Assuming you have an eye icon resource
        } else {
            binding.passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.passwordToggle.setImageResource(R.drawable.ic_eye) // Assuming you have an eye-off icon resource
        }
        isPasswordVisible = !isPasswordVisible
        binding.passwordEditText.setSelection(binding.passwordEditText.text.length) // Move cursor to the end
    }

    private fun navigateToHomeActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()  // Optionally finish LoginActivity to prevent going back to it
    }
}
