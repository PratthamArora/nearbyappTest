package com.example.nearbyapp.data.di

import android.content.Context
import com.example.nearbyapp.api.CityApi
import com.example.nearbyapp.data.db.LocationDao
import com.example.nearbyapp.data.db.LocationDatabase
import com.example.nearbyapp.repo.CityRepository
import com.example.nearbyapp.repo.ICityRepository
import com.example.nearbyapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCityApi(): CityApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CityApi::class.java)

    }

    @Singleton
    @Provides
    fun provideLocationDB(@ApplicationContext context: Context): LocationDatabase {
        return LocationDatabase.getInstance(context)

    }

    @Singleton
    @Provides
    fun provideLocationDao(db: LocationDatabase): LocationDao {
        return db.locationDao()

    }

    @Singleton
    @Provides
    fun provideCityRepository(api: CityApi, dao: LocationDao): ICityRepository = CityRepository(
        api = api,
        dao = dao
    )
}