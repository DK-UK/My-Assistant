package com.example.productive._ui.reminder

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log

class ScheduleAlarmSingleton private constructor(private val context: Context) {

    private val alarmManager: AlarmManager by lazy {
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleOrUpdateAlarm(timeInMillis: Long, requestCode: Int, title: String){
        val time = System.currentTimeMillis() + 2000
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("UNIQUE_ID", requestCode)
        intent.putExtra("TITLE", title)
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent)

        Log.e("Dhaval", "ALARM SCHEDULED", )
    }

    companion object {
        private var instance: ScheduleAlarmSingleton? = null

        fun getInstance(context: Context): ScheduleAlarmSingleton {
            return instance ?: synchronized(this) {
                instance ?: ScheduleAlarmSingleton(context).also { instance = it }
            }
        }
    }
}
