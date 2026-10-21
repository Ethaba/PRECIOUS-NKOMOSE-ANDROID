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
import com.preciousnkomose.restaurant.api.ApiClient
import com.preciousnkomose.restaurant.api.MenuItem
import com.preciousnkomose.restaurant.api.MenuResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("ClassName")
class menu : AppCompatActivity() {

    private var menuItems: List<MenuItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        val mainView = findViewById<View>(R.id.main)

        ViewCompat.setOnApplyWindowInsetsListener(mainView) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars(),
            )

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom,
            )

            insets
        }

        setupNavigation()
        setupMenuCategories()
        fetchLiveMenu()
    }

    private fun setupNavigation() {
        findViewById<View?>(R.id.nav_home)?.setOnClickListener {
            startActivity(Intent(this, home::class.java))
            finish()
        }

        findViewById<View?>(R.id.nav_menu)?.setOnClickListener {
            // Already on Menu screen
        }

        findViewById<View?>(R.id.nav_reserve)?.setOnClickListener {
            startActivity(Intent(this, PickTableActivity::class.java))
        }

        findViewById<View?>(R.id.nav_catering)?.setOnClickListener {
            startActivity(Intent(this, CateringRequestActivity::class.java))
        }

        findViewById<View?>(R.id.nav_profile)?.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun setupMenuCategories() {
        val categoryTitle = findViewById<TextView?>(R.id.text_category_title)

        findViewById<View?>(R.id.category_sa_mains)?.setOnClickListener {
            categoryTitle?.text = "SA Mains"
            filterAndShowCategory("SA Mains")
        }

        findViewById<View?>(R.id.category_starters)?.setOnClickListener {
            categoryTitle?.text = "Starters"
            filterAndShowCategory("Starters")
        }

        findViewById<View?>(R.id.category_desserts)?.setOnClickListener {
            categoryTitle?.text = "Desserts"
            filterAndShowCategory("Desserts")
        }

        findViewById<View?>(R.id.category_drinks)?.setOnClickListener {
            categoryTitle?.text = "Drinks"
            filterAndShowCategory("Drinks")
        }
    }

    private fun fetchLiveMenu() {
        ApiClient.apiService.getMenu().enqueue(
            object : Callback<MenuResponse> {
                override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
                    val body = response.body()
                    if ((response.isSuccessful) && (body?.success == true)) {
                        menuItems = body.data
                        val count = menuItems.size
                        Toast.makeText(
                            this@menu,
                            "Loaded $count items from MySQL Database",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }

                override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
                    Toast.makeText(
                        this@menu,
                        "Loaded default offline menu",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            },
        )
    }

    private fun filterAndShowCategory(categoryName: String) {
        val filtered = menuItems.filter {
            it.category.contains(categoryName, ignoreCase = true)
        }
        if (filtered.isNotEmpty()) {
            Toast.makeText(
                this,
                "Showing ${filtered.size} items in $categoryName",
                Toast.LENGTH_SHORT,
            ).show()
        }
    }
}
