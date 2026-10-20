package com.preciousnkomose.restaurant

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CateringRequestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catering_request)

        val etFullName = findViewById<EditText>(R.id.etCateringName)
        val etEmail = findViewById<EditText>(R.id.etCateringEmail)
        val etEventType = findViewById<EditText>(R.id.etCateringEventType)
        val etGuestCount = findViewById<EditText>(R.id.etCateringGuestCount)
        val btnSubmit = findViewById<Button>(R.id.btnSubmitCatering)

        btnSubmit.setOnClickListener {
            val name = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val eventType = etEventType.text.toString().trim()
            val guests = etGuestCount.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || eventType.isEmpty() || guests.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Catering request for $eventType submitted successfully!", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}