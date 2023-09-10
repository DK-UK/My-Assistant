package com.example.productive.data.local.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {

    @Insert
    suspend fun insertTask(task : Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task) : Int

    @Query("UPDATE Task set is_deleted=:is_deleted where unique_id = :unique_id")
    suspend fun deleteTask(unique_id : Long, is_deleted : Boolean = true) : Int

    @Query("DELETE from Task where id=:id")
    suspend fun delete(id : Int)

    @Query("SELECT * FROM Task where :whereClause=:whereClauseValue")
    suspend fun getTasks(whereClause : String, whereClauseValue : String) : List<Task>

    @Query("SELECT * FROM Task")
    fun getAllTasks() : Flow<List<Task>>

}

