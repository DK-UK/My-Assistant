package com.example.productive.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var unique_id : Long = 0,
    var type : String = "",
    var title : String = "",
    var description : String = "",
    var is_sub_task_of : Long = 0, // store unique_id of event or goal
    var due_date : Long = 0,
    var reminder_date : Long = 0, // store subtracted date from due_date to schedule for notification
    var created_at : Long = 0,
    var completed_at : Long = 0,
    var is_updated : Boolean = false,
    var is_deleted : Boolean = false
)

public fun Task.toExternalModel(
) = ExternalModel(
    id = this.id,
    unique_id = this.unique_id,
    type = this.type,
    title = this.title,
    description = this.description,
    is_sub_task_of = this.is_sub_task_of,
    due_date = this.due_date,
    reminder_date = this.reminder_date,
    created_at = this.created_at,
    completed_at = this.completed_at,
    is_updated = this.is_updated,
    is_deleted = this.is_deleted
)

@Entity(tableName = "Event")
data class Event(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var unique_id : Long = 0,
    var type : String = "",
    var title : String = "",
    var description : String = "",
    var start_date : Long = 0,
    var end_date : Long = 0,
    var reminder_date : Long = 0, // store subtracted date from due_date to schedule for notification
    var created_at : Long = 0,
    var completed_at : Long = 0,
    var is_updated : Boolean = false,
    var is_deleted : Boolean = false
)

fun Event.toExternalModel(
) = ExternalModel(
    id = this.id,
    unique_id = this.unique_id,
    type = this.type,
    title = this.title,
    description = this.description,
    start_date = this.start_date,
    end_date = this.end_date,
    reminder_date = this.reminder_date,
    created_at = this.created_at,
    completed_at = this.completed_at,
    is_updated = this.is_updated,
    is_deleted = this.is_deleted
)

@Entity(tableName = "Goal")
data class Goal(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var unique_id : Long = 0,
    var type : String = "",
    var title : String = "",
    var description : String = "",
    var start_date : Long = 0,
    var end_date : Long = 0, // relevant to due_date
    var reminder_date : Long = 0, // store subtracted date from due_date to schedule for notification
    var created_at : Long = 0,
    var completed_at : Long = 0,
    var is_updated : Boolean = false,
    var is_deleted : Boolean = false
)

fun Goal.toExternalModel(
) = ExternalModel(
    id = this.id,
    unique_id = this.unique_id,
    type = this.type,
    title = this.title,
    description = this.description,
    start_date = this.start_date,
    end_date = this.end_date,
    reminder_date = this.reminder_date,
    created_at = this.created_at,
    completed_at = this.completed_at,
    is_updated = this.is_updated,
    is_deleted = this.is_deleted
)