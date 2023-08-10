package com.example.productive.data

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import com.example.productive.data.local.db.ProductiveAppDatabase
import com.example.productive.data.local.entity.Event
import com.example.productive.data.local.entity.ExternalModel
import com.example.productive.data.local.entity.Goal
import com.example.productive.data.local.entity.Task
import com.example.productive.data.local.entity.toEvent
import com.example.productive.data.local.entity.toExternalModel
import com.example.productive.data.local.entity.toGoal
import com.example.productive.data.local.entity.toTask
import kotlinx.coroutines.flow.Flow
import java.util.Locale

class Repository(private val database: ProductiveAppDatabase) {

    //region Task Table Functions
    suspend fun insertTask(task : ExternalModel){
        if (task.type.contains("tasks")) {
            database.taskDao().insertTask(task.toTask())
        }
        else if(task.type.contains("events")){
            database.eventDao().insertEvent(task.toEvent())
        }
        else{
            database.goalDao().insertGoal(task.toGoal())
        }
    }

    suspend fun updateTask(task : ExternalModel){
        database.taskDao().updateTask(task.toTask())
        if (task.type.contains("tasks")) {
            database.taskDao().updateTask(task.toTask())
        }
        else if(task.type.contains("events")){
            database.eventDao().updateEvent(task.toEvent())
        }
        else{
            database.goalDao().updateGoal(task.toGoal())
        }
    }

    suspend fun deleteTask(task: ExternalModel /*type : String, unique_id : List<Long>*/){
//        if (type.lowercase(Locale.getDefault()).contains("tasks")) {
            Log.e("Dhaval", "REPOSITORY TASKS TYPE", )
//            database.taskDao().deleteTask(unique_id[0])
            database.taskDao().delete(task.toTask().id)
      /*  }
        else if(type.lowercase(Locale.getDefault()).contains("events")){
            Log.e("Dhaval", "REPOSITORY EVENTS TYPE", )
            database.eventDao().deleteEvent(unique_id)
        }
        else{
            Log.e("Dhaval", "REPOSITORY GOALS TYPE", )
            database.goalDao().deleteGoal(unique_id)
        }*/
    }

    suspend fun getTasks(type : String, whereClause : String, whereClauseValue : String) : List<ExternalModel>{
         database.taskDao().getTasks(whereClause, whereClauseValue)
        if (type.contains("tasks")) {
            return database.taskDao().getTasks(whereClause, whereClauseValue).map {
                it.toExternalModel()
            }
        }
        else if(type.contains("events")){
            return database.eventDao().getEvents(whereClause, whereClauseValue).map {
                it.toExternalModel()
            }
        }
        else{
            return database.goalDao().getGoals(whereClause, whereClauseValue).map {
                it.toExternalModel()
            }
        }
    }

    fun getAllTasks() : Flow<List<Task>> {
        return database.taskDao().getAllTasks()
    }
    //endregion

    fun getAllEvents() : Flow<List<Event>>{
        return database.eventDao().getAllEvents()
    }
    //endregion

    fun getAllGoals() : Flow<List<Goal>>{
        return database.goalDao().getAllGoals()
    }
    //endregion


}