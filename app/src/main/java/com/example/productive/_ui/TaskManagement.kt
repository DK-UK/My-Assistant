package com.example.productive._ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.productive.Utility.Utility
import com.example.productive._ui.reminder.ScheduleAlarmSingleton
import com.example.productive._ui.viewModels.TasksViewModel
import com.example.productive.data.local.entity.ExternalModel
import com.example.productive.data.local.entity.Task
import com.example.productive.ui.theme.Purple80
import kotlinx.coroutines.CoroutineScope
import java.util.Calendar


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

@Composable
fun TaskManagement(
    modifier: Modifier,
    taskViewModel: TasksViewModel,
    scope: CoroutineScope = rememberCoroutineScope()
) {
    val context = LocalContext.current
    val alarmManager = ScheduleAlarmSingleton.getInstance(context)

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            val tasksEventsGoalsList = taskViewModel.getAllTasksEventsGoals()
                .collectAsState(initial = emptyList<ExternalModel>()).value
            tasksEventsGoals(tasksEventsGoalsList)
        }

        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(16.dp)
        ) {

            var showDialog by remember {
                mutableStateOf(false)
            }

            FloatingActionButton(
                onClick = {
                    showDialog = true
                },
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }

            if (showDialog) {
                addTaskEventGoalDialog(
                    onDismissDialog = {
                        showDialog = false
                    },
                    viewModel = taskViewModel,
                    alarmManager = alarmManager,
                    context = context
                )
            }
        }
    }
}

@Composable
fun tasksEventsGoals(tasksEventsGoalsList: List<ExternalModel>) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        // Get the data of tasks, events, goals from DB
        // append the count to each

        Log.e("Dhaval", "tasksEventsGoals: value : ${tasksEventsGoalsList}")

        managementList.forEach {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addTaskEventGoalDialog(
    onDismissDialog: () -> Unit,
    viewModel: TasksViewModel,
    alarmManager: ScheduleAlarmSingleton,
    context: Context
) {
    val uniqueId = Utility.generateUniqueId()

    var isTitleEmpty by  rememberSaveable {
        mutableStateOf(false)
    }
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }

    // Type (task, event, goal)
    var typeSelected by remember {
        mutableStateOf(managementList[0])
    }

    var reminderSelected by remember {
        mutableStateOf(reminderList[0])
    }

    var cal = Calendar.getInstance()
    cal.add(Calendar.HOUR_OF_DAY, 1)
    var dueDateTime by remember {
        mutableStateOf(Utility.convertMillisToDate(cal.timeInMillis))
    }
    var fromTime by remember {
        mutableStateOf(Utility.convertMillisToDate(cal.timeInMillis))
    }
    var toTime by remember {
        mutableStateOf(Utility.convertMillisToDate(cal.timeInMillis))
    }
    var notifyMe by rememberSaveable {
        mutableStateOf(false)
    }

    Dialog(
        onDismissRequest = onDismissDialog,
        properties = DialogProperties(),
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .background(color = Color.White)
                .padding(20.dp)
                .verticalScroll(state = rememberScrollState())
        ) {

            createDropDownMenu(
                menuTitle = "Type",
                dropDownMenuList = managementList,
                typeSelected = typeSelected,
                onMenuSelected = {
                    typeSelected = it
                }
            )
            // Title
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    if(it.length > 0){
                        isTitleEmpty = false
                    }
                },
                label = {
                    Text(text = "Title")
                },
                isError = isTitleEmpty,
                supportingText = {
                                 if (isTitleEmpty){
                                     Text(text = "Required field!",
                                         color = MaterialTheme.colorScheme.error)
                                 }
                },
                trailingIcon = {
                    if (isTitleEmpty) {
                        Icon(imageVector = Icons.Filled.Info, contentDescription = "Error",
                            tint = MaterialTheme.colorScheme.error)
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            // Description
            OutlinedTextField(value = description, onValueChange = {
                description = it
            },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Description")
                })


            if (typeSelected.equals("Tasks", ignoreCase = true)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Text(text = "Due : ", style = MaterialTheme.typography.bodyLarge)
                    Text(text = dueDateTime,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .clickable {
                                showDateTimePicker(context) {
                                    dueDateTime = Utility.convertMillisToDate(it.timeInMillis)
                                }
                            }
                            .drawBehind {
                                this.drawRoundRect(
                                    color = Purple80,
                                    cornerRadius = CornerRadius(7f, 7f),
                                    alpha = 0.6f
                                )
                            }
                            .padding(5.dp))
                }
            }
            else {
                // For Events and Goals

                Column() {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 5.dp)
                    ) {

                        Text(text = "From : ", style = MaterialTheme.typography.bodyLarge)
                        Text(text = fromTime,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .clickable {
                                    showDateTimePicker(context) {
                                        fromTime = Utility.convertMillisToDate(it.timeInMillis)
                                    }
                                }
                                .drawBehind {
                                    this.drawRoundRect(
                                        color = Purple80,
                                        cornerRadius = CornerRadius(7f, 7f),
                                        alpha = 0.6f
                                    )
                                }
                                .padding(5.dp))
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 5.dp)
                    ) {

                        Text(text = "To : ", style = MaterialTheme.typography.bodyLarge)
                        Text(text = toTime,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .clickable {
                                    showDateTimePicker(context) {
                                        toTime = Utility.convertMillisToDate(it.timeInMillis)
                                    }
                                }
                                .drawBehind {
                                    this.drawRoundRect(
                                        color = Purple80,
                                        cornerRadius = CornerRadius(7f, 7f),
                                        alpha = 0.6f
                                    )
                                }
                                .padding(5.dp))
                    }
                }

            }

            // Notify preference
            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    ) {
                        notifyMe = !notifyMe
                    },
                verticalAlignment = Alignment.CenterVertically,
            ){
                Text(text = "Notify me",
                    style = MaterialTheme.typography.bodyLarge)

                Checkbox(checked = notifyMe, onCheckedChange = {
                    notifyMe = it
                    Log.e("Dhaval", "CHECKBOX : ${it}", )
                })
            }
            if (notifyMe) {
                // Notify
                createDropDownMenu(
                    menuTitle = "Notify before ",
                    dropDownMenuList = reminderList,
                    endingText = " mins",
                    typeSelected = reminderSelected,
                    onMenuSelected = {
                        reminderSelected = it
                    }
                )
            }
            // cancel and create button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                OutlinedButton(
                    onClick = onDismissDialog,
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text(text = "Cancel")
                }

                Button(
                    onClick = {

                        if (title.trim().isEmpty()){
                            isTitleEmpty = true
                        }
                        else {
                            val dueDateTime = Utility.convertStringToMillis(dueDateTime)
                            val task = Task(
                                unique_id = uniqueId,
                                type = typeSelected,
                                title = title,
                                description = description,
                                due_date = dueDateTime,
                                reminder_date = (reminderSelected.toLong() * 1000),
                                created_at = cal.timeInMillis
                            )
                            viewModel.insertTask(task)

                            // schecule alarm on task creation
                            alarmManager.scheduleOrUpdateAlarm(
                                dueDateTime - (reminderSelected.toLong() * 60 * 1000),
                                uniqueId.toInt()
                            )
                            onDismissDialog.invoke()
                        }
                    },
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text(text = "Create")
                }
            }
        }
    }
}

private fun showDateTimePicker(context: Context, onDateTimeSelected: (Calendar) -> Unit) {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    val mHour = c[Calendar.HOUR_OF_DAY]
    val mMinute = c[Calendar.MINUTE]

    val dateTimeValue = Calendar.getInstance()

    val timePicker = TimePickerDialog(
        /* context = */ context,
        /* listener = */ object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                dateTimeValue.set(Calendar.MINUTE, p2)
                dateTimeValue.set(Calendar.HOUR_OF_DAY, p1)
                // pass date and time
                onDateTimeSelected.invoke(dateTimeValue)
            }
        },
        /* hourOfDay = */ mHour,
        /* minute = */ mMinute,
        /* is24HourView = */ false
    )
    val datePicker = DatePickerDialog(
        context,
        object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
                Log.e("Dhaval", "onDateSet: day : ${day} -- month : ${month} -- year : ${year}")
                dateTimeValue.set(Calendar.DAY_OF_MONTH, day)
                dateTimeValue.set(Calendar.MONTH, month)
                dateTimeValue.set(Calendar.YEAR, year)
                timePicker.show()
            }

        },
        year,
        month,
        day
    )
    datePicker.show()
}

@Composable
fun createDropDownMenu(
    menuTitle: String,
    dropDownMenuList: List<String>,
    endingText: String = "",
    typeSelected: String,
    onMenuSelected: (String) -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Text(text = menuTitle, style = MaterialTheme.typography.bodyLarge)

        Row(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .background(
                    shape = RoundedCornerShape(5f, 5f, 5f, 5f),
                    color = MaterialTheme.colorScheme.primaryContainer
                )
                .padding(horizontal = 8.dp)
                .clickable {
                    isExpanded = !isExpanded
                }
        ) {
            var rotate by remember {
                mutableStateOf(0)
            }
            LaunchedEffect(key1 = isExpanded) {
                if (isExpanded)
                    rotate = -180
                else
                    rotate = 0
            }

            val rotateArrow = animateIntAsState(targetValue = rotate)

            Text(
                text = typeSelected,
                style = MaterialTheme.typography.bodyMedium
            )
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "arrow down",
                modifier = Modifier.rotate(rotateArrow.value.toFloat())
            )
        }
        DropdownMenu(
            expanded = isExpanded, onDismissRequest = { isExpanded = false },
            modifier = Modifier
                .weight(1f)
                .background(color = Color.White)
        ) {
            dropDownMenuList.forEach {
                DropdownMenuItem(text = {
                    Text(text = it)
                }, onClick = {
                    isExpanded = !isExpanded
                    onMenuSelected.invoke(it)
                })
            }
        }
        Text(
            text = endingText,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun prevTaskManagement() {
//    TaskManagement(modifier = Modifier.padding(5.dp), taskViewModel = viewModel())
    /*addTaskEventGoalDialog {

    }*/
}