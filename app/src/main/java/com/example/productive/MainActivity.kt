package com.example.productive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.productive._ui.CountDownScreen
import com.example.productive._ui.SplashScreen
import com.example.productive._ui.graph.navGraph
import com.example.productive._ui.viewModels.TasksViewModel
import com.example.productive.ui.theme.ProductiveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProductiveTheme {
                val navController = rememberNavController()
                val taskViewModel : TasksViewModel = viewModel()
                SplashScreen(navController = navController,
                    taskViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navHostController: NavHostController,
    taskViewModel: TasksViewModel
) {

    var showTimerScreen by remember{
        mutableStateOf(false)
    }
    var hour by remember {
        mutableStateOf(0)
    }
    var min by remember {
        mutableStateOf(0)
    }
    var sec by remember {
        mutableStateOf(0)
    }


    val onShowCountDownTimer = { hr:Int, mins:Int, secs:Int ->
        hour = hr
        min = mins
        sec = secs
    }

    if (showTimerScreen){
        CountDownScreen(hours = hour, mins = min, secs = sec,
            onTimerStop = {
                showTimerScreen = !showTimerScreen
                navHostController.navigate(NavDestinations.FOCUS_TIMER.name)
            })
    }
    else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = (navHostController.currentDestination?.route) ?: "Dashboard",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    modifier = Modifier,
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                )
            },
            bottomBar = {
                BottomNavBar(navHostController = navHostController)
            }
        ) { it ->
            navGraph(
                navController = navHostController,
                modifier = Modifier.padding(it),
                taskViewModel,
                onShowCountDownTimer = onShowCountDownTimer
            ) {
                showTimerScreen = !showTimerScreen
            }
        }
    }
}

@Composable
fun BottomNavBar(
    navHostController: NavHostController,
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {

            IconButton(onClick = {
                navHostController.navigate(NavDestinations.DASHBOARD.name)
            }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Filled.Home, contentDescription = "Dashboard")
                    Text(text = "Dashboard",
                        modifier = Modifier.wrapContentWidth())
                }
            }
            IconButton(onClick = {
                navHostController.navigate(NavDestinations.TASK_MANAGEMENT.name)
            }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(painterResource(id = R.drawable.ic_task), contentDescription = "Dashboard")
                    Text(text = "Tasks")
                }
            }

            IconButton(onClick = {
                navHostController.navigate(NavDestinations.FOCUS_TIMER.name)
            }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(painterResource(id = R.drawable.ic_alarm), contentDescription = "Dashboard")
                    Text(text = "Timer")
                }
            }

            IconButton(onClick = {
                navHostController.navigate(NavDestinations.INSIGHT.name)
            }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(painterResource(id = R.drawable.ic_chart), contentDescription = "Dashboard")
                    Text(text = "Insights")
                }
            }

            IconButton(onClick = {
                navHostController.navigate(NavDestinations.SETTINGS.name)
            }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Filled.Settings, contentDescription = "Dashboard")
                    Text(text = "Settings")
                }
            }
        }
    }
}

@Preview
@Composable
fun prevMainScreen() {
//    MainScreen(navHostController = rememberNavController())
}