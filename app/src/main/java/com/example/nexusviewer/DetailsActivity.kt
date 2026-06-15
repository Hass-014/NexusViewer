package com.example.nexusviewer

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        supportActionBar?.title = "Animal Details"

        val details =
            findViewById<TextView>(
                R.id.detailsText
            )

        val data =
            intent.getStringExtra(
                "DETAILS"
            )

        details.text = data
    }
}