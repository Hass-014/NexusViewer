package com.example.nexusviewer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface DashboardApiService {

    @GET("/dashboard/{keypass}")
    fun getDashboard(
        @Path("keypass") keypass: String
    ): Call<DashboardResponse>
}

class DashboardActivity : AppCompatActivity() {

    private lateinit var titleText: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var logoutBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)

        titleText = findViewById(R.id.titleText)
        recyclerView = findViewById(R.id.entityRecyclerView)
        logoutBtn = findViewById(R.id.logoutBtn)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val keypass = intent.getStringExtra("KEYPASS") ?: ""

        titleText.text = "Dashboard: $keypass"

        logoutBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://nit3213api.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(DashboardApiService::class.java)

        api.getDashboard(keypass).enqueue(object : Callback<DashboardResponse> {
            override fun onResponse(
                call: Call<DashboardResponse>,
                response: Response<DashboardResponse>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()?.entities ?: emptyList()
                    recyclerView.adapter = EntityAdapter(list)
                } else {
                    titleText.text = "Dashboard Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                titleText.text = "Network Error"
            }
        })
    }
}