package com.example.productive._ui.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context : Context?, intent : Intent?) {
        Log.e("Dhaval", "onReceive: CALLED", )
        // show notification for due task
    }
}