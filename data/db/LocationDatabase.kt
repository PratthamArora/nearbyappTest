package com.example.nearbyapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nearbyapp.data.model.Venue

@Database(entities = [Venue::class], version = 3, exportSchema = false)
abstract class LocationDatabase : RoomDatabase() {

    abstract fun locationDao(): LocationDao

    // Add companion object to implement Singleton pattern
    companion object {
        // Volatile to ensure the value is always up-to-date
        @Volatile
        private var INSTANCE: LocationDatabase? = null

        fun getInstance(context: Context): LocationDatabase {
            // If the INSTANCE is not null, then return it; otherwise, create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocationDatabase::class.java,
                    "location_database_name"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}