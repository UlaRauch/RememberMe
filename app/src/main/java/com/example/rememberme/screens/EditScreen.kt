package com.example.rememberme.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.work.WorkManager
import com.example.rememberme.models.Reminder
import com.example.rememberme.repositories.RememberRepository
import com.example.rememberme.viewmodels.EditRememberViewModel
import com.example.rememberme.viewmodels.EditRememberViewModelFactory

/**
 * Creats a atopAppBar for edit screen and actionButton at the bottom of the screen
 *
 * @param navController
 * @param repository: RemeberRepository
 * @param workManager
 * @param reminderID: Long
 * @param context
 */
@Composable
fun EditScreen(
    navController: NavController,
    repository: RememberRepository,
    workManager: WorkManager,
    reminderID: Long = 1,
    context: Context
) {
    val editViewModel: EditRememberViewModel = viewModel(
        factory = EditRememberViewModelFactory(
            repository = repository,
            workManager = workManager,
            reminderId = reminderID
        )
    )

    val reminder =
        editViewModel.reminder.observeAsState()  // observe the livedata as state for recomposition

    Log.d("editscreen", "you're in the editscreen")
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit ${reminder.value?.title}"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Arrow Back")
                    }
                },
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editViewModel.updateReminder()
                    navController.popBackStack()
                },
                modifier = Modifier.size(80.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Add Button",
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        content = {
            // only show card if reminder is not null
            reminder.value?.let { reminder ->
                EditReminderCard(
                    editViewModel = editViewModel,
                    context = context,
                    reminder = reminder
                )
            }
        })
}

/**
 * Content for edit screen with date/timepicker, outlined textfields and a radiobutton for a surprise date
 * @param editViewModel
 * @param reminder
 * @param context
 */
@Composable
fun EditReminderCard(
    editViewModel: EditRememberViewModel,
    reminder: Reminder,
    context: Context,
) {
    //Load reminder into ViewModel
    editViewModel.initializeReminder()

    // initialize calendar with current date
    val calDateTime = Calendar.getInstance()
    // for surprise reminder
    val surpriseDate by remember { mutableStateOf(calDateTime) } //TODO: muss das stateful sein?
    var isSurprise by remember { mutableStateOf(reminder.isSurprise) }

    // initialize stateful vars from reminder
    var y: Int by remember { mutableStateOf(reminder.y) }
    var m: Int by remember { mutableStateOf(reminder.m) }
    var d: Int by remember { mutableStateOf(reminder.d) }

    var h: Int by remember { mutableStateOf(reminder.h) }
    var min: Int by remember { mutableStateOf(reminder.min) }

    var text by remember { mutableStateOf(reminder.text) }
    var title by remember { mutableStateOf(reminder.title) }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            /*
            BEGIN REFERENCE
            Author: Leon Freudenthaler
             */
            OutlinedTextField(
                value = title,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "EditIcon"
                    )
                },
                onValueChange = { value ->
                    title = value    // update text value inside field
                    editViewModel.setTitle(value)
                },
                label = { Text(text = reminder.title) },
                placeholder = { Text(text = "Edit Title") },
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            )

            /*
             made with Tutorial: https://www.geeksforgeeks.org/date-picker-in-android-using-jetpack-compose/
             edited by Ula Rauch and Anna Leitner
             Beginn
             */
            val datePickerDialog = DatePickerDialog(
                context,
                { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                    y = year
                    //months are represented as index https://developer.android.com/reference/java/util/Date.html#Date%28int,%20int,%20int,%20int,%20int,%20int%29
                    m = month
                    d = dayOfMonth
                    editViewModel.setDate(d = d, m = m, y = y)
                    // is not a surprise reminder
                    editViewModel.setSurprise(false)
                    isSurprise = false
                }, y, m, d
            )

            Button(
                onClick = {
                    datePickerDialog.show()
                }, modifier = Modifier
                    .padding(20.dp, 5.dp)
                    .fillMaxWidth()
            ) {
                if (isSurprise == true) {
                    Text(text = "Change date")
                } else {
                    Text(
                        text = "${d.toString().padStart(2, '0')}.${
                            (m + 1).toString().padStart(2, '0')
                        }.$y"
                    )
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            /*
            End
             */

            /*
             made with Tutorial: https://www.geeksforgeeks.org/time-picker-in-android-using-jetpack-compose/
             edited by Ula Rauch and Anna Leitner
             Beginn
             */
            val mTimePickerDialog = TimePickerDialog(
                context,
                { _, mHour: Int, mMinute: Int ->
                    h = mHour
                    min = mMinute
                    editViewModel.setTime(h = h, min = min)
                    // is not a surprise reminder
                    editViewModel.setSurprise(false)
                    isSurprise = false
                }, h, min, false
            )

            Button(
                onClick = {
                    mTimePickerDialog.show()
                }, modifier = Modifier
                    .padding(20.dp, 5.dp)
                    .fillMaxWidth()
            ) {
                if (isSurprise) {
                    Text(text = "Change time")
                } else {
                    Text(
                        text = "${h.toString().padStart(2, '0')}:${
                            (min).toString().padStart(2, '0')
                        }"
                    )
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            /*
            END
             */

            //Random -> Surprise
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    modifier = Modifier.padding(20.dp, 5.dp),
                    selected = isSurprise,
                    onClick = {
                        if (!isSurprise) {
                            //Reset to current date/time is important to prevent cumulating dates when the radiobutton is clicked several times
                                surpriseDate.set(
                                calDateTime.get(Calendar.YEAR),
                                calDateTime.get(Calendar.MONTH),
                                calDateTime.get(Calendar.DAY_OF_MONTH),
                                calDateTime.get(Calendar.HOUR_OF_DAY),
                                calDateTime.get(Calendar.MINUTE)
                            )
                            // set new random date + time within interval minDays - maxDays
                            val minDays = 2
                            val maxDays = 61
                            val randomDays = kotlin.random.Random.nextInt(minDays, maxDays)
                            surpriseDate.add(Calendar.DAY_OF_MONTH, randomDays)
                            // a limitation to daytime notifications is possible by setting minHours and MaxHours to the desired interval
                            val minHours = 0
                            val maxHours = 24
                            val randomMinutes =
                                kotlin.random.Random.nextInt(minHours, ((maxHours * 60) - 1))
                            surpriseDate.add(Calendar.MINUTE, randomMinutes)
                            Log.i("Add", "New surprise date: ${surpriseDate.time}")

                            // set surprise time for reminder
                            y = surpriseDate.get(Calendar.YEAR)
                            m = surpriseDate.get(Calendar.MONTH)
                            d = surpriseDate.get(Calendar.DAY_OF_MONTH)
                            h = surpriseDate.get(Calendar.HOUR_OF_DAY)
                            min = surpriseDate.get(Calendar.MINUTE)
                            editViewModel.setDate(d, m, y)
                            editViewModel.setTime(h, min)
                            editViewModel.setSurprise(true) // is a surprise reminder
                        } else {
                            editViewModel.setSurprise(false)
                        }
                        isSurprise = !isSurprise
                        Log.i(
                            "Edit",
                            "isSurprise in VM is now: ${editViewModel.reminder.value?.isSurprise}"
                        )
                    }
                )
                Text(
                    text = "Surprise me!",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            /*
            BEGIN REFERENCE
            Author: Leon Freudenthaler
            */
            OutlinedTextField(
                value = text,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "EditIcon"
                    )
                },
                onValueChange = { value ->
                    text = value    // update text value inside field
                    editViewModel.setText(value) // update value in viewmodel
                },
                label = { Text(text = reminder.text) },
                placeholder = { Text(text = "Edit Text") },
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            )
            /*
            END
             */
        }
    }
}


