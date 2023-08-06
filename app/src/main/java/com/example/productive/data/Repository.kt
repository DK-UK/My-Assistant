package com.example.productive.data

import com.example.productive.data.local.db.ProductiveAppDatabase
import com.example.productive.data.local.entity.Event
import com.example.productive.data.local.entity.Goal
import com.example.productive.data.local.entity.Task
import kotlinx.coroutines.flow.Flow

class Repository(private val database: ProductiveAppDatabase) {

    //region Task Table Functions
    suspend fun insertTask(task : Task){
        database.taskDao().insertTask(task)
    }

    suspend fun updateTask(task : Task){
        database.taskDao().updateTask(task)
    }

    suspend fun deleteTask(vararg unique_id : String){
        database.taskDao().deleteTask(*unique_id)
    }

    suspend fun getTasks(whereClause : String, whereClauseValue : String) : List<Task>{
        return database.taskDao().getTasks(whereClause, whereClauseValue)
    }

    fun getAllTasks() : Flow<List<Task>> {
        return database.taskDao().getAllTasks()
    }
    //endregion

    //region Event Table Functions
    suspend fun insertEvent(event : Event){
        database.eventDao().insertEvent(event)
    }

    suspend fun updateEvent(event : Event){
        database.eventDao().updateEvent(event)
    }

    suspend fun deleteEvent(vararg unique_id : String){
        database.eventDao().deleteEvent(*unique_id)
    }

    suspend fun getEvents(whereClause : String, whereClauseValue : String){
        database.eventDao().getEvents(whereClause, whereClauseValue)
    }

    fun getAllEvents() : Flow<List<Event>>{
        return database.eventDao().getAllEvents()
    }
    //endregion

    //region Goal Table Functions
    suspend fun insertGoal(goal : Goal){
        database.goalDao().insertGoal(goal)
    }

    suspend fun updateGoal(goal : Goal){
        database.goalDao().updateGoal(goal)
    }

    suspend fun deleteGoal(vararg unique_id : String){
        database.goalDao().deleteGoal(*unique_id)
    }

    suspend fun getGoals(whereClause : String, whereClauseValue : String){
        database.goalDao().getGoals(whereClause, whereClauseValue)
    }

    fun getAllGoals() : Flow<List<Goal>>{
        return database.goalDao().getAllGoals()
    }
    //endregion


}