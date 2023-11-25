package com.example.nearbyapp.api

import com.example.nearbyapp.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// use DI to inject this into Repository

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: CityApi by lazy {
            retrofit.create(CityApi::class.java)
        }
    }
}