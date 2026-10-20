package com.preciousnkomose.restaurant.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    // 10.0.2.2 is the special IP address used by Android Emulator to connect to localhost on PC
    private const val BASE_URL = "http://10.0.2.2/PRECIOUS-NKOMOSE-main/PRECIOUS-NKOMOSE-main/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
