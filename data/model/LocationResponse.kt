package com.example.nearbyapp.data.model


import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("meta")
    val meta: Meta?,
    @SerializedName("venues")
    val venues: List<Venue>?
)