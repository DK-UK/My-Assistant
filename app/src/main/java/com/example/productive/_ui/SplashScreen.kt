package com.example.productive._ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.productive.MainScreen
import com.example.productive._ui.viewModels.TasksViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

@Composable
fun SplashScreen(
    navController: NavHostController,
    taskViewModel: TasksViewModel
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            try {
                taskViewModel.postTasks()
            }
            catch (e : Exception){
                
            }
        }
    }

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