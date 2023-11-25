package com.example.nearbyapp.api

import com.example.nearbyapp.data.model.LocationResponse
import com.example.nearbyapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

// create an interceptor to add the query params to all request
interface CityApi {
    @GET("venues")
    suspend fun searchCity(
        @QueryMap options: Map<String, String>,
        @Query("client_id") text: String = Constants.USER_NAME,
        @Query("per_page") maxRows: Int = Constants.MAX_ROWS,
    ): Response<LocationResponse>
}
