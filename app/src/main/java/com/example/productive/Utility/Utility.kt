package com.example.productive.Utility

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object Utility {

    @SuppressLint("NewApi")
    fun convertMillisToDate(millis : Long): String {
        val outputFormat = SimpleDateFormat("d MMM yy h:mm a", Locale.getDefault())
        val date = Date(millis)
        return outputFormat.format(date)
    }
}