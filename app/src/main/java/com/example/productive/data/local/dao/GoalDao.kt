package com.example.productive.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.productive.data.local.entity.Event
import com.example.productive.data.local.entity.Goal

@Dao
interface GoalDao {
    @Insert
    suspend fun insertGoal(goal : Goal)

    @Update
    suspend fun updateGoal(goal : Goal)

    @Query("DELETE from Goal where unique_id=:unique_id")
    suspend fun deleteGoal(vararg unique_id : String)

    @Query("SELECT * FROM Goal where :whereClause=:whereClauseValue")
    suspend fun getGoals(whereClause : String, whereClauseValue : String)
}