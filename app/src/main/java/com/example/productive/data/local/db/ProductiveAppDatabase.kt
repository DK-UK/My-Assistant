package com.example.productive.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.productive.data.local.dao.EventDao
import com.example.productive.data.local.dao.GoalDao
import com.example.productive.data.local.entity.Event
import com.example.productive.data.local.entity.Goal
import com.example.productive.data.local.entity.Task
import com.example.productive.data.local.entity.TasksDao

@Database(entities = [Task::class, Event::class, Goal::class], version = 1, exportSchema = false)
abstract class ProductiveAppDatabase : RoomDatabase() {

    abstract fun taskDao() : TasksDao
    abstract fun eventDao() : EventDao
    abstract fun goalDao() : GoalDao

    companion object {
        private var INSTANCE : ProductiveAppDatabase? = null
        fun getInstance(context: Context): ProductiveAppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    ProductiveAppDatabase::class.java,
                    "ProductiveDB"
                ).build()
            }
            return INSTANCE!!
        }
    }
}