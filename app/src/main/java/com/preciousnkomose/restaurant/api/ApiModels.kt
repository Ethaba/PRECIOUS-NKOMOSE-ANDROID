package com.preciousnkomose.restaurant.api

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("phone") val phone: String = ""
)

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class LoginResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("user") val user: User? = null
)

data class RegisterRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("password") val password: String
)

data class RegisterResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("user_id") val userId: String? = null
)

data class MenuItem(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category: String,
    @SerializedName("price") val price: Double,
    @SerializedName("available") val available: Int,
    @SerializedName("image") val image: String,
    @SerializedName("popular") val popular: Int
)

data class MenuResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: List<MenuItem> = emptyList()
)

data class ReservationRequest(
    @SerializedName("customer_id") val customerId: String,
    @SerializedName("customer_name") val customerName: String,
    @SerializedName("customer_email") val customerEmail: String,
    @SerializedName("preferred_section") val preferredSection: String,
    @SerializedName("section_label") val sectionLabel: String,
    @SerializedName("capacity") val capacity: Int,
    @SerializedName("date") val date: String,
    @SerializedName("time") val time: String,
    @SerializedName("guests") val guests: Int,
    @SerializedName("notes") val notes: String
)

data class ReservationResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)

data class UserReservation(
    @SerializedName("id") val id: String = "",
    @SerializedName("customer_id") val customerId: String = "",
    @SerializedName("preferred_section") val preferredSection: String = "",
    @SerializedName("section_label") val sectionLabel: String = "",
    @SerializedName("capacity") val capacity: Int = 4,
    @SerializedName("date") val date: String = "",
    @SerializedName("time") val time: String = "",
    @SerializedName("guests") val guests: Int = 2,
    @SerializedName("notes") val notes: String = "",
    @SerializedName("status") val status: String = "Confirmed",
    @SerializedName("created_at") val createdAt: String = ""
)

data class UserReservationsResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<UserReservation> = emptyList()
)

data class CateringRequestItem(
    @SerializedName("id") val id: String = "",
    @SerializedName("customer_id") val customerId: String = "",
    @SerializedName("event_type") val eventType: String = "",
    @SerializedName("event_date") val eventDate: String = "",
    @SerializedName("guest_count") val guestCount: Int = 0,
    @SerializedName("location") val location: String = "",
    @SerializedName("notes") val notes: String = "",
    @SerializedName("quote") val quote: String = "",
    @SerializedName("quote_sent_at") val quoteSentAt: String = "",
    @SerializedName("status") val status: String = "Pending",
    @SerializedName("created_at") val createdAt: String = ""
)

data class UserCateringResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<CateringRequestItem> = emptyList()
)
