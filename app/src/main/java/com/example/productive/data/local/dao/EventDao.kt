package com.example.productive.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.productive.data.local.entity.Event
import com.example.productive.data.local.entity.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert
    suspend fun insertEvent(event : Event)

    @Update
    suspend fun updateEvent(event: Event)

    @Query("UPDATE Event set is_deleted=:is_deleted where unique_id=:unique_id")
    suspend fun deleteEvent(unique_id : Long, is_deleted : Boolean = true)

    @Query("SELECT * FROM Event where :whereClause=:whereClauseValue")
    suspend fun getEvents(whereClause : String, whereClauseValue : String) : List<Event>

    @Query("SELECT * FROM Event")
    fun getAllEvents() : Flow<List<Event>>
}