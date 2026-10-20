package com.preciousnkomose.restaurant

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.preciousnkomose.restaurant.api.UserSession

class ProfileActivity : AppCompatActivity() {

    private lateinit var tvProfileName: TextView
    private lateinit var tvProfileEmail: TextView
    private lateinit var tvProfileAvatar: TextView
    private lateinit var session: UserSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        session = UserSession(this)

        tvProfileName = findViewById(R.id.tvProfileName)
        tvProfileEmail = findViewById(R.id.tvProfileEmail)
        tvProfileAvatar = findViewById(R.id.tvProfileAvatar)

        findViewById<Button>(R.id.btnEditProfile).setOnClickListener {
            showEditProfileDialog()
        }

        findViewById<View>(R.id.rowReservations).setOnClickListener {
            navigateTo(PickTableActivity::class.java)
        }

        findViewById<View>(R.id.rowReviews).setOnClickListener {
            navigateTo(ReviewsActivity::class.java)
        }

        findViewById<View>(R.id.rowCatering).setOnClickListener {
            navigateTo(CateringRequestActivity::class.java)
        }

        findViewById<View>(R.id.rowContact).setOnClickListener {
            navigateTo(ContactActivity::class.java)
        }

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            session.logout()
            Toast.makeText(this, "Signed out successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        setupNavigation()
        updateProfileDisplay()
    }

    private fun showEditProfileDialog() {
        val user = session.getUser()

        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 12, 48, 0)
        }

        val nameInput = EditText(this).apply {
            hint = "Full name"
            setText(user.name.takeUnless { it == "Guest Customer" } ?: "")
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
        }

        val emailInput = EditText(this).apply {
            hint = "Email address"
            setText(user.email.takeUnless { it == "customer@example.com" } ?: "")
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }

        container.addView(nameInput)
        container.addView(emailInput)

        AlertDialog.Builder(this)
            .setTitle("Edit Profile")
            .setView(container)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Save") { _, _ ->
                val enteredName = nameInput.text.toString().trim()
                val enteredEmail = emailInput.text.toString().trim()

                if (enteredName.isEmpty()) {
                    Toast.makeText(this, "Please enter your name.", Toast.LENGTH_SHORT).show()
                } else if (enteredEmail.isNotEmpty() &&
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(enteredEmail).matches()
                ) {
                    Toast.makeText(this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show()
                } else {
                    // For now we only update the local session (full backend update can be added later)
                    val updated = user.copy(
                        name = enteredName,
                        email = if (enteredEmail.isNotEmpty()) enteredEmail else user.email
                    )
                    session.saveUser(updated)
                    updateProfileDisplay()
                    Toast.makeText(this, "Profile updated for this session.", Toast.LENGTH_SHORT).show()
                }
            }
            .show()
    }

    private fun updateProfileDisplay() {
        val user = session.getUser()
        tvProfileName.text = user.name
        tvProfileEmail.text = if (user.email.isNotEmpty() && user.email != "customer@example.com") {
            user.email
        } else {
            "Add your email address"
        }
        tvProfileAvatar.text = user.name.firstOrNull()?.uppercase() ?: "G"
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
            // Already on Profile
        }
    }

    private fun navigateTo(destination: Class<*>) {
        startActivity(Intent(this, destination))
        finish()
    }
}