package com.example.nearbyapp.data.model


import com.google.gson.annotations.SerializedName

data class Stats(
    @SerializedName("event_count")
    val eventCount: Int?
)