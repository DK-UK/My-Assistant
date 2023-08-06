package com.example.productive._ui.viewModels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productive.data.Repository
import com.example.productive.data.local.db.ProductiveAppDatabase
import com.example.productive.data.local.entity.Event
import com.example.productive.data.local.entity.ExternalModel
import com.example.productive.data.local.entity.Goal
import com.example.productive.data.local.entity.Task
import com.example.productive.data.local.entity.toExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TasksViewModel(
    /*val repository: Repository*/
    val app : Application
) : AndroidViewModel(app) {

    private lateinit var repository: Repository

    init{
        val db = ProductiveAppDatabase.getInstance(app)
        repository = Repository(db)
    }

    val allTasks: StateFlow<List<Task>> = getAllTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )


    fun getAllTasksEventsGoals(): Flow<MutableList<ExternalModel>> {
        val tasksFlow: Flow<List<Task>> = repository.getAllTasks()
        val eventsFlow: Flow<List<Event>> = repository.getAllEvents()
        val goalsFlow: Flow<List<Goal>> = repository.getAllGoals()

        // Combine the flows
        val externalModelList = mutableListOf<ExternalModel>()
        val combinedFlow = combine(tasksFlow, eventsFlow, goalsFlow) { tasks, events, goals ->

//            Log.e("Dhaval", "getAllTasksEventsGoals: tasks : ${tasks.get(0).title} -- events : ${events.get(0).title} -- goals : ${goals.get(0).title}", )

            externalModelList.addAll(tasks.map { it.toExternalModel() })
            externalModelList.addAll(events.map { it.toExternalModel() })
            externalModelList.addAll(goals.map { it.toExternalModel() })
            externalModelList
        }
        return combinedFlow
    }

    //region Tasks Functions
    fun insertTask(task : Task){
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }

    fun updateTask(task : Task){
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteTask(vararg uniuqe_id : String){
        viewModelScope.launch {
            repository.deleteTask(*uniuqe_id)
        }
    }

    fun getAllTasks() : Flow<List<Task>> {
        return repository.getAllTasks()
    }

    fun getAllEvents() : Flow<List<Event>> {
        return repository.getAllEvents()
    }

    fun getAllGoals() : Flow<List<Goal>> {
        return repository.getAllGoals()
    }
    //endregion
}
