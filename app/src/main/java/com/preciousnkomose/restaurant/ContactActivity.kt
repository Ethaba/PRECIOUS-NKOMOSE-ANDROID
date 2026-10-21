package com.preciousnkomose.restaurant

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val etSubject = findViewById<EditText>(R.id.etContactSubject)
        val etMessage = findViewById<EditText>(R.id.etContactMessage)
        val btnSend = findViewById<Button>(R.id.btnSendMessage)

        btnBack?.setOnClickListener {
            finish()
        }

        btnSend?.setOnClickListener {
            val subject = etSubject.text.toString().trim()
            val message = etMessage.text.toString().trim()

            if (subject.isEmpty() || message.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please fill in both subject and message before sending",
                    Toast.LENGTH_SHORT,
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Thank you! Your message has been sent.",
                    Toast.LENGTH_LONG,
                ).show()
                finish()
            }
        }

        setupNavigation()
    }

    private fun setupNavigation() {
        findViewById<android.view.View?>(R.id.nav_home)?.setOnClickListener {
            startActivity(Intent(this, home::class.java))
            finish()
        }

        findViewById<android.view.View?>(R.id.nav_menu)?.setOnClickListener {
            startActivity(Intent(this, menu::class.java))
            finish()
        }

        findViewById<android.view.View?>(R.id.nav_reserve)?.setOnClickListener {
            startActivity(Intent(this, PickTableActivity::class.java))
            finish()
        }

        findViewById<android.view.View?>(R.id.nav_catering)?.setOnClickListener {
            startActivity(Intent(this, CateringRequestActivity::class.java))
            finish()
        }

        findViewById<android.view.View?>(R.id.nav_profile)?.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }
}
