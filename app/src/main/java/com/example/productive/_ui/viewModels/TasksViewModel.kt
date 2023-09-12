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
import com.example.productive.data.remote.RetrofitHelper
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
        val apiService = RetrofitHelper.getApiService()
        repository = Repository(db, apiService)
    }

/*
    val allTasks: StateFlow<List<Task>> = getAllTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )
*/


    fun getAllTasksEventsGoals(): Flow<List<ExternalModel>> {
        return repository.getAllTasks()
    }

    //region Tasks Functions
    fun insertTask(task : ExternalModel){
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }

    fun updateTask(task : ExternalModel){
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteTask(uniuqe_id : Long){
        viewModelScope.launch {
            repository.deleteTask(uniuqe_id)
        }
    }
    //endregion

    fun getAllTasksFromRemote() {
        viewModelScope.launch {
            repository.getTaskFromRemote()
        }
    }

    fun postTasks(){
        viewModelScope.launch {
            repository.postTasks()
        }
    }

    fun updateUserEmail(email : String){
        viewModelScope.launch {
            repository.updateUserEmail(email)
        }
    }
}
