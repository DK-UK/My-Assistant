package com.example.productive._ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.productive._ui.viewModels.TasksViewModel
import com.example.productive.data.local.entity.ExternalModel

@Composable
fun TaskManagement(
    modifier: Modifier,
    taskViewModel: TasksViewModel
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            val tasksEventsGoalsList = taskViewModel.getAllTasksEventsGoals()
                .collectAsState(initial = emptyList<ExternalModel>()).value
            tasksEventsGoals(tasksEventsGoalsList)
        }

        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(16.dp)
        ){
            FloatingActionButton(onClick = {
                                           
                                           },
                contentColor = Color.White) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }
    }
}

val managementList = mutableListOf<String>(
    "Tasks",
    "Events",
    "Goals"
)

@Composable
fun tasksEventsGoals(tasksEventsGoalsList: List<ExternalModel>) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly
    ){

        // Get the data of tasks, events, goals from DB
        // append the count to each

        Log.e("Dhaval", "tasksEventsGoals: value : ${tasksEventsGoalsList}", )

        managementList.forEach {
            Text(text = it,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Composable
fun bottomSheetAddTaskEventGoal() {
    
}

@Preview
@Composable
fun prevTaskManagement() {
    TaskManagement(modifier = Modifier.padding(5.dp), taskViewModel = viewModel())
}