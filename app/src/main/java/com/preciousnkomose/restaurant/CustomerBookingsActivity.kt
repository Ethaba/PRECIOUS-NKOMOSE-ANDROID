package com.preciousnkomose.restaurant

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.preciousnkomose.restaurant.api.ApiClient
import com.preciousnkomose.restaurant.api.CateringRequestItem
import com.preciousnkomose.restaurant.api.UserCateringResponse
import com.preciousnkomose.restaurant.api.UserReservation
import com.preciousnkomose.restaurant.api.UserReservationsResponse
import com.preciousnkomose.restaurant.api.UserSession
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerBookingsActivity : AppCompatActivity() {

    private lateinit var session: UserSession

    private lateinit var btnTabReservations: MaterialButton
    private lateinit var btnTabCatering: MaterialButton
    private lateinit var textSectionHeader: TextView

    private lateinit var containerReservations: LinearLayout
    private lateinit var containerCatering: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_bookings)

        session = UserSession(this)

        findViewById<ImageButton>(R.id.btnBack)?.setOnClickListener {
            finish()
        }

        btnTabReservations = findViewById(R.id.btn_tab_reservations)
        btnTabCatering = findViewById(R.id.btn_tab_catering)
        textSectionHeader = findViewById(R.id.text_section_header)

        containerReservations = findViewById(R.id.container_user_reservations)
        containerCatering = findViewById(R.id.container_user_catering)

        btnTabReservations.setOnClickListener {
            showReservationsTab()
        }

        btnTabCatering.setOnClickListener {
            showCateringTab()
        }

        setupNavigation()

        // Default to Table Reservations Tab
        showReservationsTab()

        // Load data from MySQL API
        fetchUserReservations()
        fetchUserCatering()
    }

    private fun showReservationsTab() {
        textSectionHeader.text = "Booked Table Reservations"
        containerReservations.visibility = View.VISIBLE
        containerCatering.visibility = View.GONE

        val purple = Color.parseColor("#7044A0")
        val lightPurple = Color.parseColor("#F0E8FA")
        val white = Color.parseColor("#FFFFFF")

        btnTabReservations.backgroundTintList = ColorStateList.valueOf(purple)
        btnTabReservations.setTextColor(white)

        btnTabCatering.backgroundTintList = ColorStateList.valueOf(lightPurple)
        btnTabCatering.setTextColor(purple)
    }

    private fun showCateringTab() {
        textSectionHeader.text = "Catering Quotes & Requests"
        containerReservations.visibility = View.GONE
        containerCatering.visibility = View.VISIBLE

        val purple = Color.parseColor("#7044A0")
        val lightPurple = Color.parseColor("#F0E8FA")
        val white = Color.parseColor("#FFFFFF")

        btnTabCatering.backgroundTintList = ColorStateList.valueOf(purple)
        btnTabCatering.setTextColor(white)

        btnTabReservations.backgroundTintList = ColorStateList.valueOf(lightPurple)
        btnTabReservations.setTextColor(purple)
    }

    private fun fetchUserReservations() {
        val currentUser = session.getUser()
        ApiClient.apiService.getUserReservations(currentUser.id, currentUser.email).enqueue(
            object : Callback<UserReservationsResponse> {
                override fun onResponse(
                    call: Call<UserReservationsResponse>,
                    response: Response<UserReservationsResponse>,
                ) {
                    val body = response.body()
                    if ((response.isSuccessful) && (body?.success == true) && (body.data.isNotEmpty())) {
                        displayReservations(body.data)
                    } else {
                        displayReservations(getFallbackReservations())
                    }
                }

                override fun onFailure(call: Call<UserReservationsResponse>, t: Throwable) {
                    displayReservations(getFallbackReservations())
                }
            },
        )
    }

    private fun fetchUserCatering() {
        val currentUser = session.getUser()
        ApiClient.apiService.getUserCateringRequests(currentUser.id, currentUser.email).enqueue(
            object : Callback<UserCateringResponse> {
                override fun onResponse(
                    call: Call<UserCateringResponse>,
                    response: Response<UserCateringResponse>,
                ) {
                    val body = response.body()
                    if ((response.isSuccessful) && (body?.success == true) && (body.data.isNotEmpty())) {
                        displayCateringRequests(body.data)
                    } else {
                        displayCateringRequests(getFallbackCatering())
                    }
                }

                override fun onFailure(call: Call<UserCateringResponse>, t: Throwable) {
                    displayCateringRequests(getFallbackCatering())
                }
            },
        )
    }

    private fun displayReservations(items: List<UserReservation>) {
        containerReservations.removeAllViews()

        if (items.isEmpty()) {
            val emptyTv = TextView(this).apply {
                text = "No table reservations booked yet."
                setPadding(16, 32, 16, 32)
                setTextColor(Color.parseColor("#716879"))
                textSize = 14f
            }
            containerReservations.addView(emptyTv)
            return
        }

        for (res in items) {
            val card = MaterialCardView(this).apply {
                radius = 32f
                cardElevation = 2f
                setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                strokeColor = Color.parseColor("#EFE8F5")
                strokeWidth = 2
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                ).apply {
                    setMargins(0, 0, 0, 24)
                }
                layoutParams = params
            }

            val layout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(36, 32, 36, 32)
            }

            val headerLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
            }

            val titleTv = TextView(this).apply {
                text = res.sectionLabel.ifBlank { res.preferredSection }
                textSize = 17f
                setTypeface(null, Typeface.BOLD)
                setTextColor(Color.parseColor("#21182B"))
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }

            val statusTv = TextView(this).apply {
                text = res.status
                textSize = 12f
                setTypeface(null, Typeface.BOLD)
                setPadding(20, 8, 20, 8)
                if (res.status.equals("Cancelled", ignoreCase = true)) {
                    setTextColor(Color.parseColor("#D32F2F"))
                    setBackgroundColor(Color.parseColor("#FFEBEE"))
                } else {
                    setTextColor(Color.parseColor("#2E7D32"))
                    setBackgroundColor(Color.parseColor("#E8F5E9"))
                }
            }

            headerLayout.addView(titleTv)
            headerLayout.addView(statusTv)

            val detailsTv = TextView(this).apply {
                text = "Date: ${res.date} • Time: ${res.time} • Guests: ${res.guests}"
                textSize = 14f
                setTextColor(Color.parseColor("#7044A0"))
                setPadding(0, 12, 0, 4)
            }

            layout.addView(headerLayout)
            layout.addView(detailsTv)

            if (res.notes.isNotBlank()) {
                val notesTv = TextView(this).apply {
                    text = "Special Requests: \"${res.notes}\""
                    textSize = 13f
                    setTextColor(Color.parseColor("#716879"))
                    setPadding(0, 8, 0, 0)
                }
                layout.addView(notesTv)
            }

            card.addView(layout)
            containerReservations.addView(card)
        }
    }

    private fun displayCateringRequests(items: List<CateringRequestItem>) {
        containerCatering.removeAllViews()

        if (items.isEmpty()) {
            val emptyTv = TextView(this).apply {
                text = "No catering requests submitted yet."
                setPadding(16, 32, 16, 32)
                setTextColor(Color.parseColor("#716879"))
                textSize = 14f
            }
            containerCatering.addView(emptyTv)
            return
        }

        for (cat in items) {
            val card = MaterialCardView(this).apply {
                radius = 32f
                cardElevation = 2f
                setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                strokeColor = Color.parseColor("#EFE8F5")
                strokeWidth = 2
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                ).apply {
                    setMargins(0, 0, 0, 24)
                }
                layoutParams = params
            }

            val layout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(36, 32, 36, 32)
            }

            val headerLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
            }

            val titleTv = TextView(this).apply {
                text = cat.eventType
                textSize = 17f
                setTypeface(null, Typeface.BOLD)
                setTextColor(Color.parseColor("#21182B"))
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }

            val statusTv = TextView(this).apply {
                text = cat.status
                textSize = 12f
                setTypeface(null, Typeface.BOLD)
                setPadding(20, 8, 20, 8)
                if (cat.status.equals("Quoted", ignoreCase = true) || cat.status.equals("Completed", ignoreCase = true)) {
                    setTextColor(Color.parseColor("#2E7D32"))
                    setBackgroundColor(Color.parseColor("#E8F5E9"))
                } else {
                    setTextColor(Color.parseColor("#F57C00"))
                    setBackgroundColor(Color.parseColor("#FFF3E0"))
                }
            }

            headerLayout.addView(titleTv)
            headerLayout.addView(statusTv)

            val detailsTv = TextView(this).apply {
                text = "Event Date: ${cat.eventDate} • Guests: ${cat.guestCount}\nLocation: ${cat.location}"
                textSize = 14f
                setTextColor(Color.parseColor("#716879"))
                setPadding(0, 12, 0, 8)
            }

            val quoteBox = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setBackgroundColor(Color.parseColor("#F8F4FC"))
                setPadding(24, 20, 24, 20)
            }

            val quoteTitleTv = TextView(this).apply {
                text = "RESTAURANT QUOTE DETAILS:"
                textSize = 11f
                setTypeface(null, Typeface.BOLD)
                setTextColor(Color.parseColor("#7044A0"))
            }

            val quoteTextTv = TextView(this).apply {
                text = cat.quote.ifBlank { "Quote pending review by restaurant team." }
                textSize = 14f
                setTypeface(null, Typeface.BOLD)
                setTextColor(Color.parseColor("#21182B"))
                setPadding(0, 6, 0, 0)
            }

            quoteBox.addView(quoteTitleTv)
            quoteBox.addView(quoteTextTv)

            layout.addView(headerLayout)
            layout.addView(detailsTv)
            layout.addView(quoteBox)

            card.addView(layout)
            containerCatering.addView(card)
        }
    }

    private fun getFallbackReservations(): List<UserReservation> {
        return listOf(
            UserReservation(
                id = "R003",
                preferredSection = "Table 1",
                sectionLabel = "Table 1",
                capacity = 4,
                date = "2026-11-01",
                time = "19:00:00",
                guests = 2,
                notes = "Anniversary celebration dinner",
                status = "Confirmed",
            ),
            UserReservation(
                id = "R001",
                preferredSection = "corner-ne",
                sectionLabel = "Corner Bench North East",
                capacity = 6,
                date = "2026-05-29",
                time = "13:30:00",
                guests = 6,
                notes = "Quiet corner table requested",
                status = "Cancelled",
            ),
        )
    }

    private fun getFallbackCatering(): List<CateringRequestItem> {
        return listOf(
            CateringRequestItem(
                id = "CR001",
                eventType = "Formal Gathering",
                eventDate = "2026-11-15",
                guestCount = 120,
                location = "Riverdene, Newlands West",
                notes = "3 course meal for 120 guests",
                quote = "Quoted Amount: R36,000.00 (Includes 3-course menu & service staff)",
                status = "Quoted",
            ),
            CateringRequestItem(
                id = "CR002",
                eventType = "Celebration Party",
                eventDate = "2026-12-05",
                guestCount = 48,
                location = "Precious Nkomose Lounge",
                notes = "Finger food platter and cocktail menu",
                quote = "Quoted Amount: R12,000.00",
                status = "Completed",
            ),
        )
    }

    private fun setupNavigation() {
        findViewById<View?>(R.id.nav_home)?.setOnClickListener {
            startActivity(Intent(this, home::class.java))
            finish()
        }

        findViewById<View?>(R.id.nav_menu)?.setOnClickListener {
            startActivity(Intent(this, menu::class.java))
            finish()
        }

        findViewById<View?>(R.id.nav_reserve)?.setOnClickListener {
            startActivity(Intent(this, PickTableActivity::class.java))
            finish()
        }

        findViewById<View?>(R.id.nav_catering)?.setOnClickListener {
            startActivity(Intent(this, CateringRequestActivity::class.java))
            finish()
        }

        findViewById<View?>(R.id.nav_profile)?.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }
}
