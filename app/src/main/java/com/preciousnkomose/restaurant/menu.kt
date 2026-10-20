
package com.preciousnkomose.restaurant

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        val mainView = findViewById<View>(R.id.main)

        ViewCompat.setOnApplyWindowInsetsListener(mainView) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        setupNavigation()
        setupMenuCategories()
    }

    private fun setupNavigation() {
        findViewById<View?>(R.id.nav_home)?.setOnClickListener {
            startActivity(Intent(this, home::class.java))
            finish()
        }

        findViewById<View?>(R.id.nav_menu)?.setOnClickListener {
            // Already on the Menu screen.
        }

        findViewById<View?>(R.id.nav_reserve)?.setOnClickListener {
            startActivity(Intent(this, PickTableActivity::class.java))
        }

        findViewById<android.view.View>(R.id.nav_catering)?.setOnClickListener {
            startActivity(Intent(this, CateringRequestActivity::class.java))
        }


        findViewById<android.view.View>(R.id.nav_profile)?.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

    }

    private fun setupMenuCategories() {
        val categoryTitle = findViewById<TextView?>(R.id.text_category_title)

        findViewById<View?>(R.id.category_sa_mains)?.setOnClickListener {
            categoryTitle?.text = "SA Mains"
        }

        findViewById<View?>(R.id.category_starters)?.setOnClickListener {
            categoryTitle?.text = "Starters"
        }

        findViewById<View?>(R.id.category_desserts)?.setOnClickListener {
            categoryTitle?.text = "Desserts"
        }

        findViewById<View?>(R.id.category_drinks)?.setOnClickListener {
            categoryTitle?.text = "Drinks"
        }
    }
}
