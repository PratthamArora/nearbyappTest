package com.example.nearbyapp.data.model


import com.google.gson.annotations.SerializedName

data class Geolocation(
    @SerializedName("city")
    val city: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("display_name")
    val displayName: String?,
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?,
    @SerializedName("metro_code")
    val metroCode: Any?,
    @SerializedName("postal_code")
    val postalCode: String?,
    @SerializedName("range")
    val range: String?,
    @SerializedName("state")
    val state: String?
)