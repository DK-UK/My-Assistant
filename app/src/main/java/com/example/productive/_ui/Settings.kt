package com.example.productive._ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    modifier : Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
    ){
        Column(){
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ){
                Text(text = "Sync to cloud",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.clickable {

                    })
                Text(text = "dk@gmail.com",
                    style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}