package com.example.nearbyapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nearbyapp.data.model.Venue

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<Venue>)

    @Query("SELECT * FROM venue_table")
    fun getAllEntities(): List<Venue>?
}