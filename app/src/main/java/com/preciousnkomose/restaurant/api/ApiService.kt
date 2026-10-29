package com.preciousnkomose.restaurant.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("api/login.php")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("api/register.php")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @GET("api/menu.php")
    fun getMenu(): Call<MenuResponse>

    @POST("api/reserve.php")
    fun createReservation(@Body request: ReservationRequest): Call<ReservationResponse>

    @GET("api/user_reservations.php")
    fun getUserReservations(
        @Query("customer_id") customerId: String,
        @Query("email") email: String
    ): Call<UserReservationsResponse>

    @GET("api/user_catering.php")
    fun getUserCateringRequests(
        @Query("customer_id") customerId: String,
        @Query("email") email: String
    ): Call<UserCateringResponse>
}
