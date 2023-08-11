package com.example.productive._ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.productive.Utility.Utility
import com.example.productive._ui.reminder.ScheduleAlarmSingleton
import com.example.productive._ui.viewModels.TasksViewModel
import com.example.productive.data.local.entity.ExternalModel

@Composable
fun Dashboard(
    modifier : Modifier = Modifier,
    taskViewModel : TasksViewModel
) {
    val context = LocalContext.current
    val alarmManager = ScheduleAlarmSingleton.getInstance(context)

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column {
            Text(text = "Today's due",
                style = MaterialTheme.typography.bodyLarge
                    .copy(fontWeight = FontWeight.ExtraBold),
                modifier = Modifier.padding(10.dp))

            val tasksEventsGoalsList = taskViewModel.getAllTasksEventsGoals()
                .collectAsState(initial = emptyList<ExternalModel>()).value.filter {
                    Utility.compareTwoDates(it.due_date, Utility.getTodaysDateInMillis()) ||
                            Utility.compareTwoDates(it.end_date, Utility.getTodaysDateInMillis())
                }

            tasksEventsGoalsListUI(tasksEventsGoalsList,
                viewModel = taskViewModel,
                alarmManager = alarmManager,
                context = context)
        }
    }
}

@Preview
@Composable
fun prevDashboard() {
    /*Dashboard(
        Dashboard(taskViewModel = viewModel())
    )*/
}