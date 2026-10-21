package com.preciousnkomose.restaurant.api

import android.os.Build
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val PATH = "PRECIOUS-NKOMOSE-main/PRECIOUS-NKOMOSE-main/"

    // PC local Wi-Fi IP address for physical devices
    private const val WIFI_IP = "192.168.8.106"

    // Android Emulator host loopback IP
    private const val EMULATOR_IP = "10.0.2.2"

    private fun isEmulator(): Boolean {
        return ((Build.FINGERPRINT.startsWith("generic")) ||
                (Build.MODEL.contains("google_sdk")) ||
                (Build.MODEL.contains("Emulator")) ||
                (Build.MODEL.contains("Android SDK built for x86")) ||
                (Build.MANUFACTURER.contains("Genymotion")) ||
                (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) ||
                ("google_sdk" == Build.PRODUCT))
    }

    private val baseUrl: String
        get() {
            val ip = if (isEmulator()) EMULATOR_IP else WIFI_IP
            return "http://$ip/$PATH"
        }

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
