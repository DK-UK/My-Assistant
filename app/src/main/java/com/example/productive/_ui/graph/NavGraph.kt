package com.example.productive._ui.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.productive.NavDestinations
import com.example.productive._ui.Dashboard
import com.example.productive._ui.TaskManagement

@Composable
fun navGraph(
    navController: NavHostController,
    modifier : Modifier = Modifier
) {
    NavHost(navController = navController,
        startDestination = NavDestinations.DASHBOARD.name){

        composable(route = NavDestinations.DASHBOARD.name){
            Dashboard(modifier)
        }

        composable(route = NavDestinations.TASK_MANAGEMENT.name){
            TaskManagement(modifier)
        }

        composable(route = NavDestinations.FOCUS_TIMER.name){

        }

        composable(route = NavDestinations.INSIGHT.name){

        }

        composable(route = NavDestinations.SETTINGS.name){

        }
    }
}