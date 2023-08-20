package com.example.productive.Utility

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

object Utility {

    val managementList = mutableListOf<String>(
        "Tasks",
        "Events",
        "Goals"
    )
    val reminderList = mutableListOf<String>(
        "1",
        "5",
        "10",
        "30",
        "60",
        "120"
    )

    @SuppressLint("NewApi")
    fun convertMillisToDate(millis : Long): String {
        val outputFormat = SimpleDateFormat("d MMM yy h:mm a", Locale.getDefault())
        val date = Date(millis)
        return outputFormat.format(date)
    }

    fun compareTwoDates(firstInMillis : Long, secondInMillis : Long) : Boolean {
        val outputFormat = SimpleDateFormat("d MMM yy", Locale.getDefault())
        val first = Date(firstInMillis)
        val second = Date(secondInMillis)
        return outputFormat.format(first) == outputFormat.format(second)
    }

    fun convertStringToMillis(dateStr : String): Long {
        val outputFormat = SimpleDateFormat("d MMM yy h:mm a", Locale.getDefault())
        val date = outputFormat.parse(dateStr)
        return date.time
    }
    fun generateUniqueId() : Long {
        val uuid = UUID.randomUUID()
        return uuid.mostSignificantBits
    }

    fun convertMinsToMillis(mins : Long) : Long {
        return (60 * 1000 * mins)
    }

    fun convertMillisToMins(millis : Long) : Long {
        return (millis / (60 * 1000))
    }

    fun getTodaysDateInMillis() : Long {
        val cal = Calendar.getInstance()
        return cal.timeInMillis
    }

    fun secondsToMinutesSeconds(seconds: Int): String {

        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

    fun formatTimerDigits(timeDigit : Int) : String{
        return String.format("%02d", timeDigit)
    }
    fun convertLongToTime(long : Long, type : String): Int {
        val cal = Calendar.getInstance()
        cal.timeInMillis = long

        return when (type) {
            "HOUR" -> {
                cal.get(Calendar.HOUR_OF_DAY)
            }

            "MIN" -> {
                cal.get(Calendar.MINUTE)
            }

            "SEC" -> {
                cal.get(Calendar.SECOND)
            }

            else -> 0
        }
    }

}