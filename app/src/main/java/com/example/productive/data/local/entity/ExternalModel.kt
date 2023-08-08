package com.example.productive.data.local.entity

import androidx.room.PrimaryKey

data class ExternalModel(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    var unique_id : Long = 0,
    var type : String = "",
    var title : String = "",
    var description : String = "",
    var is_sub_task_of : String = "", // store unique_id of event or goal
    var due_date : Long = 0,
    var start_date : Long = 0,
    var end_date : Long = 0, // relevant to due_date
    var reminder_date : Long = 0, // store subtracted date from due_date to schedule for notification
    var created_at : Long = 0,
    var completed_at : Long = 0
)
