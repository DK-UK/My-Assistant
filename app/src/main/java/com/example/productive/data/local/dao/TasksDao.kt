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
    suspend fun insertTask(task : ExternalModel)

    @Update
    suspend fun updateTask(task: ExternalModel)

    @Delete
    suspend fun deleteTask(task: ExternalModel) : Int

    @Query("UPDATE Tasks set is_deleted=:is_deleted and is_updated = :is_deleted where unique_id = :unique_id") // both update and deleted should be true
    suspend fun deleteTask(unique_id : Long, is_deleted : Boolean = true) : Int

    @Query("DELETE from Tasks where id=:id")
    suspend fun delete(id : Int)

    @Query("SELECT * FROM Tasks where :whereClause=:whereClauseValue and type = :type")
    suspend fun getTasks(type : String, whereClause : String, whereClauseValue : String) : List<ExternalModel>

    @Query("UPDATE Tasks set email = :email")
    suspend fun updateUserEmail(email : String)

    @Query("SELECT * FROM Tasks where is_updated = :is_updated")
    suspend fun getUpdatedTasks(is_updated : Boolean = true) : List<ExternalModel>
    @Query("SELECT * FROM Tasks")
    fun getAllTasks() : Flow<List<ExternalModel>>

}

