package com.preciousnkomose.restaurant

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ReviewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)

        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val etReviewText = findViewById<EditText>(R.id.etReviewComment)
        val btnSubmit = findViewById<Button>(R.id.btnSubmitReview)

        btnSubmit.setOnClickListener {
            val rating = ratingBar.rating
            val review = etReviewText.text.toString().trim()

            if (rating == 0f) {
                Toast.makeText(this, "Please select a star rating", Toast.LENGTH_SHORT).show()
            } else if (review.isEmpty()) {
                Toast.makeText(this, "Please write a review comment", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Thank you for your review!", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}
