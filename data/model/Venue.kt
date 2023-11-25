package com.example.nearbyapp.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "venue_table")
data class Venue(
    @SerializedName("address")
    var address: String? = "",
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @SerializedName("name_v2")
    var nameV2: String = "",
)