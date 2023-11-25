package com.example.nearbyapp.repo

import com.example.nearbyapp.api.CityApi
import com.example.nearbyapp.data.db.LocationDao
import com.example.nearbyapp.data.model.LocationResponse
import com.example.nearbyapp.data.model.Venue
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Response
import javax.inject.Inject

class CityRepository  @Inject constructor(
    private val api: CityApi,
    private val dao: LocationDao
) : ICityRepository {


    override suspend fun addVenues(venues: List<Venue>) {
        dao.insertAll(venues)
    }

    override suspend fun getLastStoredVenuesFromDB(): List<Venue>? {
        val cachedVenues = dao.getAllEntities()
        return cachedVenues
    }

    override suspend fun searchCity(searchQuery: MutableMap<String, String>): Response<LocationResponse> {
        return api.searchCity(searchQuery)
    }

}