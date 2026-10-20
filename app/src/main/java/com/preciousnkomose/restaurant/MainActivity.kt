package com.preciousnkomose.restaurant

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.preciousnkomose.restaurant.api.ApiClient
import com.preciousnkomose.restaurant.api.LoginRequest
import com.preciousnkomose.restaurant.api.LoginResponse
import com.preciousnkomose.restaurant.api.UserSession
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var registerText: TextView
    private lateinit var forgotPasswordText: TextView
    private lateinit var session: UserSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        session = UserSession(this)

        if (session.isLoggedIn()) {
            startActivity(Intent(this, home::class.java))
            finish()
            return
        }

        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)
        registerText = findViewById(R.id.registerText)
        forgotPasswordText = findViewById(R.id.forgotPasswordText)

        loginButton.setOnClickListener {
            validateLoginForm()
        }

        registerText.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        forgotPasswordText.setOnClickListener {
            Toast.makeText(
                this,
                "Please contact support or use your registered email to reset password.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun validateLoginForm() {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString()

        if (email.isEmpty()) {
            emailInput.error = "Please enter your email address"
            emailInput.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.error = "Please enter a valid email address"
            emailInput.requestFocus()
            return
        }

        if (password.isEmpty()) {
            passwordInput.error = "Please enter your password"
            passwordInput.requestFocus()
            return
        }

        loginButton.isEnabled = false
        loginButton.text = "Logging in..."

        val request = LoginRequest(email = email, password = password)
        ApiClient.apiService.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                loginButton.isEnabled = true
                loginButton.text = "Login"

                val body = response.body()
                if (response.isSuccessful && body?.success == true && body.user != null) {
                    session.saveUser(body.user)
                    Toast.makeText(
                        this@MainActivity,
                        "Welcome back, ${body.user.name}!",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(Intent(this@MainActivity, home::class.java))
                    finish()
                } else {
                    val errorMsg = body?.message ?: "Login failed. Please check your credentials."
                    Toast.makeText(this@MainActivity, errorMsg, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginButton.isEnabled = true
                loginButton.text = "Login"
                Toast.makeText(
                    this@MainActivity,
                    "Connection failed: ${t.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
