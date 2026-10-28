package com.preciousnkomose.restaurant.api

import android.os.Build
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    // Primary cloud API host (accessible permanently on any Android device anywhere in the world)
    private const val CLOUD_BASE_URL = "https://preciousnkomose.co.za/"

    // Local development API path
    private const val LOCAL_PATH = "PRECIOUS-NKOMOSE-main/PRECIOUS-NKOMOSE-main/"

    // Local Wi-Fi IPv4 address for physical devices connected to XAMPP
    private const val LOCAL_WIFI_IP = "192.168.8.106"

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
            val ip = if (isEmulator()) EMULATOR_IP else LOCAL_WIFI_IP
            return "http://$ip/$LOCAL_PATH"
        }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
