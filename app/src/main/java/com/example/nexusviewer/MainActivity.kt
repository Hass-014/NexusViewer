package com.example.nexusviewer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val keypass: String?
)

interface ApiService {

    @POST("/sydney/auth")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>
}

class MainActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var forgotPassword: TextView
    private lateinit var errorText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginButton)
        forgotPassword = findViewById(R.id.forgotPassword)
        errorText = findViewById(R.id.errorText)

        val retrofit =
            Retrofit.Builder()
                .baseUrl("https://nit3213api.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val api =
            retrofit.create(ApiService::class.java)

        forgotPassword.setOnClickListener {
            errorText.text =
                "Please contact your unit coordinator to reset your password."
        }

        loginButton.setOnClickListener {

            val user =
                username.text.toString().trim()

            val pass =
                password.text.toString().trim()

            if (user.isEmpty() || pass.isEmpty()) {
                errorText.text =
                    "Enter username and password"
                return@setOnClickListener
            }

            errorText.text =
                "Checking login..."

            val request =
                LoginRequest(
                    user,
                    pass
                )

            api.login(request)
                .enqueue(
                    object : Callback<LoginResponse> {

                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {

                            if (response.isSuccessful) {

                                val intent =
                                    Intent(
                                        this@MainActivity,
                                        DashboardActivity::class.java
                                    )

                                intent.putExtra(
                                    "KEYPASS",
                                    response.body()?.keypass
                                )

                                startActivity(intent)
                                finish()

                            } else {

                                errorText.text =
                                    "Server Error: ${response.code()}"
                            }
                        }

                        override fun onFailure(
                            call: Call<LoginResponse>,
                            t: Throwable
                        ) {

                            errorText.text =
                                "Network Error"
                        }
                    }
                )
        }
    }
}