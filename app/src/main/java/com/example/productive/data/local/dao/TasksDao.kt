package com.example.productive.data.local.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TasksDao {

    @Insert
    suspend fun insertTask(task : Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("DELETE from Task where unique_id=:unique_id")
    suspend fun deleteTask(vararg unique_id : String)

    @Query("SELECT * FROM Task where :whereClause=:whereClauseValue")
    suspend fun getTasks(whereClause : String, whereClauseValue : String)
}

