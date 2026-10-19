
package com.preciousnkomose.restaurant

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        val etSubject = findViewById<EditText>(R.id.etContactSubject)
        val etMessage = findViewById<EditText>(R.id.etContactMessage)
        val btnSend = findViewById<Button>(R.id.btnSendMessage)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        // Return to the previous screen.
        btnBack.setOnClickListener {
            finish()
        }

        // Validate the contact form.
        btnSend.setOnClickListener {
            val subject = etSubject.text.toString().trim()
            val message = etMessage.text.toString().trim()

            if (subject.isEmpty() || message.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter both a subject and a message",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Message sent successfully!",
                    Toast.LENGTH_LONG
                ).show()

                // Submission is simulated until backend/email
                // integration is implemented.
                finish()
            }
        }

        setupNavigation()
    }

    private fun setupNavigation() {
        findViewById<View>(R.id.nav_home).setOnClickListener {
            navigateTo(home::class.java)
        }

        findViewById<View>(R.id.nav_menu).setOnClickListener {
            navigateTo(menu::class.java)
        }

        findViewById<View>(R.id.nav_reserve).setOnClickListener {
            navigateTo(PickTableActivity::class.java)
        }

        findViewById<View>(R.id.nav_catering).setOnClickListener {
            navigateTo(CateringRequestActivity::class.java)
        }

        findViewById<View>(R.id.nav_profile).setOnClickListener {
            Toast.makeText(
                this,
                "Profile functionality will be added later.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun navigateTo(destination: Class<*>) {
        startActivity(Intent(this, destination))
        finish()
    }
}
