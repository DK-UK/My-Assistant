package com.example.productive.data.local.entity

import androidx.room.PrimaryKey

data class ExternalModel(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    var unique_id : String = "",
    var type : String = "",
    var title : String = "",
    var description : String = "",
    var is_sub_task_of : String = "", // store unique_id of event or goal
    var due_date : String = "",
    var start_date : String = "",
    var end_date : String = "", // relevant to due_date
    var reminder_date : String = "", // store subtracted date from due_date to schedule for notification
    var created_at : String,
    var completed_at : String
)
