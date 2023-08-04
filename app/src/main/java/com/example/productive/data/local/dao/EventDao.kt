package com.example.productive.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.productive.data.local.entity.Event
import com.example.productive.data.local.entity.Task

@Dao
interface EventDao {
    @Insert
    suspend fun insertEvent(event : Event)

    @Update
    suspend fun updateEvent(event: Event)

    @Query("DELETE from Event where unique_id=:unique_id")
    suspend fun deleteEvent(vararg unique_id : String)

    @Query("SELECT * FROM Event where :whereClause=:whereClauseValue")
    suspend fun getEvents(whereClause : String, whereClauseValue : String)
}