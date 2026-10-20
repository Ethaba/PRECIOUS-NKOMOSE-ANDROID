package com.preciousnkomose.restaurant.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("api/login.php")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("api/register.php")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @GET("api/menu.php")
    fun getMenu(): Call<MenuResponse>

    @POST("api/reserve.php")
    fun createReservation(@Body request: ReservationRequest): Call<ReservationResponse>
}
