package com.example.productive._ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TaskManagement(
    modifier : Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        tasksEventsGoals()
    }
}

val managementList = mutableListOf<String>(
    "Tasks",
    "Events",
    "Goals"
)

@Composable
fun tasksEventsGoals() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly
    ){

        // Get the data of tasks, events, goals from DB
        // append the count to each

        managementList.forEach {
            Text(text = it,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Preview
@Composable
fun prevTaskManagement() {
    TaskManagement(modifier = Modifier.padding(5.dp))
}