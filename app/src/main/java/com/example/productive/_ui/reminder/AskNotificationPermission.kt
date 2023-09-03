package com.example.productive._ui.reminder

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.productive.MainActivity
import com.example.productive.R

@Composable
fun AskNotificationPermission(
    isNotificationGranted : (Boolean) -> Unit
) {
    val context = LocalContext.current
    val notificationPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()){
        isNotificationGranted.invoke(it)
    }

    LaunchedEffect(key1 = true){
        val permission = android.Manifest.permission.POST_NOTIFICATIONS

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                notificationPermission.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
            else{
                isNotificationGranted.invoke(true)
            }
        }
        else{
            isNotificationGranted.invoke(true)
        }
    }
}

fun showNotification(context : Context,
                     title : String){
    val notificationManager = BaseApp.notificationManager

    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(context, 0, intent,
        PendingIntent.FLAG_MUTABLE)
    val notification = NotificationCompat.Builder(context,
        "unique_id")
        .setSmallIcon(R.mipmap.ic_launcher)
    .setContentTitle("Pending Task")
        .setContentText(title)
    .setContentIntent(pendingIntent)
        .build()

    notificationManager.notify(1, notification)
}