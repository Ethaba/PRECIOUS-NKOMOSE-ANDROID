
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

class ProfileActivity : AppCompatActivity() {

    private lateinit var tvProfileName: TextView
    private lateinit var tvProfileEmail: TextView
    private lateinit var tvProfileAvatar: TextView

    private var profileName = "Guest Customer"
    private var profileEmail = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

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
            Toast.makeText(
                this,
                "Sign out will be available when login is connected.",
                Toast.LENGTH_SHORT
            ).show()
        }

        setupNavigation()
        updateProfileDisplay()
    }

    private fun showEditProfileDialog() {
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 12, 48, 0)
        }

        val nameInput = EditText(this).apply {
            hint = "Full name"
            setText(profileName.takeUnless {
                it == "Guest Customer"
            } ?: "")
            inputType = InputType.TYPE_CLASS_TEXT or
                    InputType.TYPE_TEXT_FLAG_CAP_WORDS
        }

        val emailInput = EditText(this).apply {
            hint = "Email address"
            setText(profileEmail)
            inputType = InputType.TYPE_CLASS_TEXT or
                    InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
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
                    Toast.makeText(
                        this,
                        "Please enter your name.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (
                    enteredEmail.isNotEmpty() &&
                    !android.util.Patterns.EMAIL_ADDRESS
                        .matcher(enteredEmail).matches()
                ) {
                    Toast.makeText(
                        this,
                        "Please enter a valid email address.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    profileName = enteredName
                    profileEmail = enteredEmail
                    updateProfileDisplay()

                    Toast.makeText(
                        this,
                        "Profile updated for this session.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .show()
    }

    private fun updateProfileDisplay() {
        tvProfileName.text = profileName
        tvProfileEmail.text = profileEmail.ifEmpty {
            "Add your email address"
        }

        tvProfileAvatar.text = profileName
            .firstOrNull()
            ?.uppercase()
            ?: "G"
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
            // Already on the Profile page.
        }
    }

    private fun navigateTo(destination: Class<*>) {
        startActivity(Intent(this, destination))
        finish()
    }
}
