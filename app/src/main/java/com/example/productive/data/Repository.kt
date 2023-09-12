package com.example.productive.data

import android.util.Log
import com.example.productive.data.local.db.ProductiveAppDatabase
import com.example.productive.data.local.entity.Event
import com.example.productive.data.local.entity.ExternalModel
import com.example.productive.data.local.entity.Goal
import com.example.productive.data.local.entity.Task
import com.example.productive.data.local.entity.toEvent
import com.example.productive.data.local.entity.toExternalModel
import com.example.productive.data.local.entity.toGoal
import com.example.productive.data.local.entity.toTask
import com.example.productive.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import java.util.Locale

class Repository(private val database: ProductiveAppDatabase,
    private val apiService: ApiService) {

    //region Task Table Functions
    suspend fun insertTask(task : ExternalModel){
        database.taskDao().insertTask(task)
    }

    suspend fun updateTask(task : ExternalModel){
        database.taskDao().updateTask(task)
    }

    suspend fun deleteTask(unique_id : Long){
        val count = database.taskDao().deleteTask(unique_id)
    }

    suspend fun getTasks(type : String, whereClause : String, whereClauseValue : String) : List<ExternalModel>{
        return database.taskDao().getTasks(type, whereClause, whereClauseValue)
    }

    fun getAllTasks() : Flow<List<ExternalModel>> {
        return database.taskDao().getAllTasks()
    }
    //endregion

    suspend fun updateUserEmail(email : String){
        database.taskDao().updateUserEmail(email)
    }

    suspend fun getTaskFromRemote()  {
        val tasks = apiService.getAllTasks()
        tasks.forEach {
            insertTask(it)
        }
    }

    suspend fun postTasks() {
        val tasks = database.taskDao().getUpdatedTasks()
        apiService.postTasks(tasks)
    }
}