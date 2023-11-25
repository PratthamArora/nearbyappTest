package com.example.nearbyapp.data.model


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("geolocation")
    val geolocation: Geolocation?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("per_page")
    val perPage: Int?,
    @SerializedName("took")
    val took: Int?,
    @SerializedName("total")
    val total: Int?
)