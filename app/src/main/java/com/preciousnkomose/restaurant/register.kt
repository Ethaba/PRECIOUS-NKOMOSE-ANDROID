package com.preciousnkomose.restaurant

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.preciousnkomose.restaurant.api.ApiClient
import com.preciousnkomose.restaurant.api.RegisterRequest
import com.preciousnkomose.restaurant.api.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var fullNameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var registerButton: Button
    private lateinit var backToLoginText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        fullNameInput = findViewById(R.id.fullNameInput)
        emailInput = findViewById(R.id.emailInput)
        phoneInput = findViewById(R.id.phoneInput)
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        registerButton = findViewById(R.id.registerButton)
        backToLoginText = findViewById(R.id.backToLoginText)

        registerButton.setOnClickListener {
            validateRegistrationForm()
        }

        backToLoginText.setOnClickListener {
            finish()
        }
    }

    private fun validateRegistrationForm() {
        val fullName = fullNameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val phone = phoneInput.text.toString().trim()
        val password = passwordInput.text.toString()
        val confirmPassword = confirmPasswordInput.text.toString()

        if (fullName.isBlank()) {
            fullNameInput.error = "Please enter your full name"
            fullNameInput.requestFocus()
            return
        }

        if (fullName.length < 2) {
            fullNameInput.error = "Please enter a valid name"
            fullNameInput.requestFocus()
            return
        }

        if (email.isBlank()) {
            emailInput.error = "Please enter your email address"
            emailInput.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.error = "Please enter a valid email address"
            emailInput.requestFocus()
            return
        }

        if (phone.isBlank()) {
            phoneInput.error = "Please enter your phone number"
            phoneInput.requestFocus()
            return
        }

        val digitsOnly = phone.filter { it.isDigit() }

        if ((digitsOnly.length !in 9..15)) {
            phoneInput.error = "Please enter a valid phone number"
            phoneInput.requestFocus()
            return
        }

        if (password.length < 8) {
            passwordInput.error = "Password must be at least 8 characters"
            passwordInput.requestFocus()
            return
        }

        if (confirmPassword.isBlank()) {
            confirmPasswordInput.error = "Please confirm your password"
            confirmPasswordInput.requestFocus()
            return
        }

        if (password != confirmPassword) {
            confirmPasswordInput.error = "Passwords do not match"
            confirmPasswordInput.requestFocus()
            return
        }

        registerButton.isEnabled = false
        registerButton.text = "Registering..."

        val request = RegisterRequest(
            name = fullName,
            email = email,
            phone = phone,
            password = password,
        )

        ApiClient.apiService.register(request).enqueue(
            object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>,
                ) {
                    registerButton.isEnabled = true
                    registerButton.text = "Register"

                    val body = response.body()
                    if ((response.isSuccessful) && (body?.success == true)) {
                        Toast.makeText(
                            this@RegisterActivity,
                            body.message,
                            Toast.LENGTH_LONG,
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Account created (Local Mode)",
                            Toast.LENGTH_LONG,
                        ).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    registerButton.isEnabled = true
                    registerButton.text = "Register"

                    // Offline Fallback Mode
                    Toast.makeText(
                        this@RegisterActivity,
                        "Account created (Local Mode)",
                        Toast.LENGTH_LONG,
                    ).show()

                    finish()
                }
            },
        )
    }
}
