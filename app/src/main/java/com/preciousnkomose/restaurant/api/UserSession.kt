package com.preciousnkomose.restaurant.api

import android.content.Context
import android.content.SharedPreferences

class UserSession(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_session_prefs", Context.MODE_PRIVATE)

    fun saveUser(user: User) {
        prefs.edit().apply {
            putString("KEY_USER_ID", user.id)
            putString("KEY_USER_NAME", user.name)
            putString("KEY_USER_EMAIL", user.email)
            putString("KEY_USER_PHONE", user.phone)
            putBoolean("KEY_IS_LOGGED_IN", true)
            apply()
        }
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("KEY_IS_LOGGED_IN", false)
    }

    fun getUser(): User {
        return User(
            id = prefs.getString("KEY_USER_ID", "U0000") ?: "U0000",
            name = prefs.getString("KEY_USER_NAME", "Guest Customer") ?: "Guest Customer",
            email = prefs.getString("KEY_USER_EMAIL", "customer@example.com") ?: "customer@example.com",
            phone = prefs.getString("KEY_USER_PHONE", "") ?: ""
        )
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}
