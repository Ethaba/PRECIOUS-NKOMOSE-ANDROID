package com.preciousnkomose.restaurant

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.preciousnkomose.restaurant.api.ApiClient
import com.preciousnkomose.restaurant.api.MenuItem
import com.preciousnkomose.restaurant.api.MenuResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("ClassName")
class menu : AppCompatActivity() {

    private var menuItems: List<MenuItem> = emptyList()

    private lateinit var categoryTitle: TextView
    private lateinit var scrollMenu: ScrollView

    private lateinit var btnMains: MaterialButton
    private lateinit var btnStarters: MaterialButton
    private lateinit var btnDesserts: MaterialButton
    private lateinit var btnDrinks: MaterialButton

    private lateinit var containerMains: View
    private lateinit var containerStarters: View
    private lateinit var containerDesserts: View
    private lateinit var containerDrinks: View

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
        categoryTitle = findViewById(R.id.text_category_title)
        scrollMenu = findViewById(R.id.scroll_menu)

        btnMains = findViewById(R.id.category_sa_mains)
        btnStarters = findViewById(R.id.category_starters)
        btnDesserts = findViewById(R.id.category_desserts)
        btnDrinks = findViewById(R.id.category_drinks)

        containerMains = findViewById(R.id.container_mains)
        containerStarters = findViewById(R.id.container_starters)
        containerDesserts = findViewById(R.id.container_desserts)
        containerDrinks = findViewById(R.id.container_drinks)

        btnMains.setOnClickListener {
            selectCategory("SA Mains", containerMains, btnMains)
        }

        btnStarters.setOnClickListener {
            selectCategory("Starters", containerStarters, btnStarters)
        }

        btnDesserts.setOnClickListener {
            selectCategory("Desserts", containerDesserts, btnDesserts)
        }

        btnDrinks.setOnClickListener {
            selectCategory("Drinks", containerDrinks, btnDrinks)
        }

        // Default category
        selectCategory("SA Mains", containerMains, btnMains)
    }

    private fun selectCategory(title: String, activeContainer: View, activeBtn: MaterialButton) {
        categoryTitle.text = title

        // Toggle container visibility
        containerMains.visibility = if (activeContainer == containerMains) View.VISIBLE else View.GONE
        containerStarters.visibility = if (activeContainer == containerStarters) View.VISIBLE else View.GONE
        containerDesserts.visibility = if (activeContainer == containerDesserts) View.VISIBLE else View.GONE
        containerDrinks.visibility = if (activeContainer == containerDrinks) View.VISIBLE else View.GONE

        // Update button styles
        val allButtons = listOf(btnMains, btnStarters, btnDesserts, btnDrinks)
        val purpleColor = Color.parseColor("#7044A0")
        val lightPurpleColor = Color.parseColor("#F0E8FA")
        val whiteColor = Color.parseColor("#FFFFFF")

        for (btn in allButtons) {
            if (btn == activeBtn) {
                btn.backgroundTintList = ColorStateList.valueOf(purpleColor)
                btn.setTextColor(whiteColor)
            } else {
                btn.backgroundTintList = ColorStateList.valueOf(lightPurpleColor)
                btn.setTextColor(purpleColor)
            }
        }

        scrollMenu.smoothScrollTo(0, 0)
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
}
