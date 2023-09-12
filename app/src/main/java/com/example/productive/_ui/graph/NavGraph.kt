package com.example.productive._ui.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.productive.NavDestinations
import com.example.productive._ui.Dashboard
import com.example.productive._ui.SettingsScreen
import com.example.productive._ui.TaskManagement
import com.example.productive._ui.Timer
import com.example.productive._ui.viewModels.TasksViewModel

@Composable
fun navGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    taskViewModel: TasksViewModel,
    onShowCountDownTimer: (Int, Int, Int) -> Unit,
    onShowTimerScreen: () -> Unit,
    onTitleChanged : (String) -> Unit
) {
    val context = LocalContext.current

    NavHost(navController = navController,
        startDestination = NavDestinations.DASHBOARD.name){

        composable(route = NavDestinations.DASHBOARD.name){
            onTitleChanged.invoke("Dashboard")
            Dashboard(modifier,
                taskViewModel)
        }

        composable(route = NavDestinations.TASK_MANAGEMENT.name){
            onTitleChanged.invoke("Task Management")
            TaskManagement(modifier, taskViewModel)
        }

        composable(route = NavDestinations.FOCUS_TIMER.name){
            onTitleChanged.invoke("Focus Timer")
            Timer(modifier,
                onShowCountDownTimer = onShowCountDownTimer,
                onShowTimerScreen
            )
        }

        composable(route = NavDestinations.INSIGHT.name){
            onTitleChanged.invoke("Insight")
            // upcoming
        }

        composable(route = NavDestinations.SETTINGS.name){
            onTitleChanged.invoke("Settings")
            SettingsScreen(context = context, modifier = modifier,
                tasksViewModel = taskViewModel)
        }
    }
}