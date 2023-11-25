package com.example.nearbyapp.repo

import com.example.nearbyapp.data.model.LocationResponse
import com.example.nearbyapp.data.model.Venue
import retrofit2.Response


interface ICityRepository {
    suspend fun addVenues(venues : List<Venue> )
    suspend fun getLastStoredVenuesFromDB(): List<Venue>?
    suspend fun searchCity(searchQuery: MutableMap<String, String>): Response<LocationResponse>
}