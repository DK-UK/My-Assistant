package com.example.productive._ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.productive.MainScreen
import com.example.productive._ui.viewModels.TasksViewModel

@Composable
fun SplashScreen(
    navController: NavHostController,
    taskViewModel: TasksViewModel
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        
    }
    Column(){
        // Image covers the whole screen
        // Get Started Button at the bottom
        
    }
    MainScreen(navHostController = navController,
        taskViewModel)
}