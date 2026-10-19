package com.preciousnkomose.restaurant

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        val etSubject = findViewById<EditText>(R.id.etContactSubject)
        val etMessage = findViewById<EditText>(R.id.etContactMessage)
        val btnSend = findViewById<Button>(R.id.btnSendMessage)

        btnSend.setOnClickListener {
            val subject = etSubject.text.toString().trim()
            val message = etMessage.text.toString().trim()

            if (subject.isEmpty() || message.isEmpty()) {
                Toast.makeText(this, "Please enter both a subject and a message", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Message sent successfully!", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}
