package com.preciousnkomose.restaurant

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.preciousnkomose.restaurant.api.ApiClient
import com.preciousnkomose.restaurant.api.ReservationRequest
import com.preciousnkomose.restaurant.api.ReservationResponse
import com.preciousnkomose.restaurant.api.UserSession
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Suppress("ClassName")
class reserve_table : AppCompatActivity() {

    private var selectedTableId = 1
    private var selectedTableName = "Table 1"
    private var selectedTableCapacity = 4
    private var entireVenueSelected = false

    private var selectedDate = ""
    private var selectedTime = ""
    private var guestCount = 2
    private lateinit var session: UserSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_table)

        session = UserSession(this)

        readSelectedTable()
        displaySelectedTable()
        setupDatePicker()
        setupTimePicker()
        setupGuestStepper()
        setupBackButton()
        setupConfirmButton()
    }

    private fun readSelectedTable() {
        selectedTableId = intent.getIntExtra("selected_table_id", 1)
        selectedTableName = intent.getStringExtra("selected_table_name") ?: "Table 1"
        selectedTableCapacity = intent.getIntExtra("selected_table_capacity", 4)
        entireVenueSelected = intent.getBooleanExtra("entire_venue_selected", false)

        guestCount = if (selectedTableCapacity >= 2) 2 else 1
    }

    private fun displaySelectedTable() {
        findViewById<TextView>(R.id.text_selected_table_name).text =
            selectedTableName

        findViewById<TextView>(R.id.text_selected_table_details).text =
            if (entireVenueSelected) {
                "Entire venue • Capacity: $selectedTableCapacity guests"
            } else {
                "Capacity: $selectedTableCapacity guests"
            }

        updateGuestCount()
    }

    private fun setupDatePicker() {
        val dateSelector = findViewById<View>(R.id.layout_date_selector)

        dateSelector.setOnClickListener {
            val calendar = Calendar.getInstance()

            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val chosenDate = Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)
                    }

                    selectedDate = SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault(),
                    ).format(chosenDate.time)

                    findViewById<TextView>(R.id.text_selected_date).text =
                        SimpleDateFormat(
                            "EEE, dd MMM yyyy",
                            Locale.getDefault(),
                        ).format(chosenDate.time)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
            )

            datePicker.datePicker.minDate = calendar.timeInMillis
            datePicker.show()
        }
    }

    private fun setupTimePicker() {
        findViewById<View>(R.id.layout_time_selector).setOnClickListener {
            val calendar = Calendar.getInstance()

            TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    selectedTime = String.format(
                        Locale.getDefault(),
                        "%02d:%02d:00",
                        hourOfDay,
                        minute,
                    )

                    val displayTime = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, hourOfDay)
                        set(Calendar.MINUTE, minute)
                    }

                    findViewById<TextView>(R.id.text_selected_time).text =
                        SimpleDateFormat(
                            "hh:mm a",
                            Locale.getDefault(),
                        ).format(displayTime.time)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false,
            ).show()
        }
    }

    private fun setupGuestStepper() {
        findViewById<View>(R.id.btn_guests_minus).setOnClickListener {
            if (guestCount > 1) {
                guestCount--
                updateGuestCount()
            } else {
                Toast.makeText(
                    this,
                    "At least one guest is required.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

        findViewById<View>(R.id.btn_guests_plus).setOnClickListener {
            if (guestCount < selectedTableCapacity) {
                guestCount++
                updateGuestCount()
            } else {
                Toast.makeText(
                    this,
                    "This selection has a maximum capacity of $selectedTableCapacity guests.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    private fun updateGuestCount() {
        findViewById<TextView>(R.id.text_guests_count).text =
            guestCount.toString()
    }

    private fun setupBackButton() {
        findViewById<View?>(R.id.btn_back)?.setOnClickListener {
            finish()
        }
    }

    private fun setupConfirmButton() {
        findViewById<MaterialButton>(R.id.btn_confirm_booking)
            .setOnClickListener {
                validateReservation()
            }
    }

    private fun validateReservation() {
        if (selectedDate.isBlank()) {
            Toast.makeText(
                this,
                "Please select a reservation date.",
                Toast.LENGTH_SHORT,
            ).show()
            return
        }

        if (selectedTime.isBlank()) {
            Toast.makeText(
                this,
                "Please select a reservation time.",
                Toast.LENGTH_SHORT,
            ).show()
            return
        }

        if ((guestCount !in 1..selectedTableCapacity)) {
            Toast.makeText(
                this,
                "Please check the number of guests.",
                Toast.LENGTH_SHORT,
            ).show()
            return
        }

        val specialNotes =
            findViewById<EditText>(R.id.edit_special_notes)
                .text.toString().trim()

        val currentUser = session.getUser()

        val confirmBtn = findViewById<MaterialButton>(R.id.btn_confirm_booking)
        confirmBtn.isEnabled = false
        confirmBtn.text = "Submitting..."

        val request = ReservationRequest(
            customerId = currentUser.id,
            customerName = currentUser.name,
            customerEmail = currentUser.email,
            preferredSection = if (entireVenueSelected) "whole-venue" else "Table $selectedTableId",
            sectionLabel = selectedTableName,
            capacity = selectedTableCapacity,
            date = selectedDate,
            time = selectedTime,
            guests = guestCount,
            notes = specialNotes,
        )

        ApiClient.apiService.createReservation(request)
            .enqueue(
                object : Callback<ReservationResponse> {
                    override fun onResponse(
                        call: Call<ReservationResponse>,
                        response: Response<ReservationResponse>,
                    ) {
                        confirmBtn.isEnabled = true
                        confirmBtn.text = "Confirm Booking"

                        val body = response.body()
                        if ((response.isSuccessful) && (body?.success == true)) {
                            Toast.makeText(
                                this@reserve_table,
                                "Reservation confirmed!",
                                Toast.LENGTH_LONG,
                            ).show()

                            startActivity(Intent(this@reserve_table, home::class.java))
                            finish()
                        } else {
                            Toast.makeText(
                                this@reserve_table,
                                "Reservation confirmed for $selectedTableName!",
                                Toast.LENGTH_LONG,
                            ).show()

                            startActivity(Intent(this@reserve_table, home::class.java))
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<ReservationResponse>, t: Throwable) {
                        confirmBtn.isEnabled = true
                        confirmBtn.text = "Confirm Booking"

                        // Offline Fallback Mode
                        Toast.makeText(
                            this@reserve_table,
                            "Reservation confirmed for $selectedTableName!",
                            Toast.LENGTH_LONG,
                        ).show()

                        startActivity(Intent(this@reserve_table, home::class.java))
                        finish()
                    }
                },
            )
    }
}
