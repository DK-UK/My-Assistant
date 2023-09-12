package com.example.productive.data.local.data_store

import android.content.Context
import android.content.SharedPreferences
import java.util.prefs.Preferences

class EmailPreference() {
    companion object{
        private var sharedPref : SharedPreferences? = null
        fun getInstance(context: Context) : EmailPreference {
            if (sharedPref == null) {
                sharedPref = context.getSharedPreferences("EMAIL_PREF", Context.MODE_PRIVATE)
            }
            return EmailPreference()
        }
    }
    fun saveEmailToPref(email : String){
        sharedPref!!.edit().putString("EMAIL", email).apply()
    }

    fun getEmailFromPref() : String {
        return sharedPref!!.getString("EMAIL", "")!!
    }
}