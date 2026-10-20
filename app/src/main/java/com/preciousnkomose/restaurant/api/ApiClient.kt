package com.preciousnkomose.restaurant.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    // Primary network IP address for PC running XAMPP (works on physical phone and emulator on Wi-Fi)
    private const val BASE_URL = "http://172.25.17.228/PRECIOUS-NKOMOSE-main/PRECIOUS-NKOMOSE-main/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
