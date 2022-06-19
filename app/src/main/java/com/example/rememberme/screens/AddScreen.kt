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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.work.WorkManager
import com.example.rememberme.navigation.RememberScreens
import com.example.rememberme.repositories.RememberRepository
import com.example.rememberme.viewmodels.AddRememberViewModel
import com.example.rememberme.viewmodels.AddRememberViewModelFactory

/**
 * Creats a topAppBar for add screen and actionButton at the bottom of the screen
 *
 * @param navController
 * @param repository: RemeberRepository
 * @param workManager
 * @param context
 */
@Composable
fun AddScreen(
    navController: NavController,
    repository: RememberRepository,
    workManager: WorkManager,
    context: Context
) {
    val addViewModel: AddRememberViewModel = viewModel(
        factory = AddRememberViewModelFactory(repository = repository, workManager = workManager)
    )

    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Text(text = "New Reminder")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack, "Arrow Back"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    addViewModel.addReminder()
                    navController.navigate(route = RememberScreens.HomeScreen.name)
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
            ReminderCard(addViewModel = addViewModel, context = context)
        }
    )
}

/**
 * Content for add screen with date/timepicker, outlined textfields and a radiobutton for a surprise date
 * @param addViewModel
 * @param context
 */
@Composable
fun ReminderCard(addViewModel: AddRememberViewModel, context: Context) {

    // Declaring and initializing a calendar with current date/time - stays on current
    val calNow = Calendar.getInstance()
    //Log.i("Add", "Calendar.getinstance(): calNow")

    // for surprise reminder
    val surpriseDate by remember { mutableStateOf(calNow) }
    var isSurprise by remember { mutableStateOf(false) }

    // initialize date with current date
    var y: Int by remember { mutableStateOf(calNow.get(Calendar.YEAR)) }
    var m: Int by remember { mutableStateOf(calNow.get(Calendar.MONTH)) }
    var d: Int by remember { mutableStateOf(calNow.get(Calendar.DAY_OF_MONTH)) }
    //nowDate.time = Date() //TODO: brauchts das? was passiert, wenn mans weglasst?
    //set reminder in VM to current date as default
    addViewModel.setDate(d = d, m = m, y = y)

    // initialize time with current time
    var h: Int by remember { mutableStateOf(calNow.get(Calendar.HOUR_OF_DAY))}
    var min: Int by remember { mutableStateOf(calNow.get(Calendar.MINUTE))}
    //set reminder in VM to current time as default
    addViewModel.setTime(h = h, min = min)

    var text by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    //val reminder: Reminder? by addViewModel.reminder.observeAsState(null)

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(4.dp)),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalArrangement = Arrangement.Center
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
                    addViewModel.setTitle(value) // update value in viewmodel
                },
                label = { Text(text = "Title of your Reminder") },
                placeholder = { Text(text = "Enter Title") },
                modifier = Modifier
                    .padding(20.dp, 30.dp)
                    .fillMaxWidth()
            )
            /*
            END
             */

            /*
            BEGIN
             made with Tutorial: https://www.geeksforgeeks.org/date-picker-in-android-using-jetpack-compose/
             edited by Ula Rauch and Anna Leitner
             */
            val datePickerDialog = DatePickerDialog(
                context,
                { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                    y = year
                    //months are represented as index https://developer.android.com/reference/java/util/Date.html#Date%28int,%20int,%20int,%20int,%20int,%20int%29
                    m = month
                    d = dayOfMonth
                    addViewModel.setDate(d = d, m = m, y = y)
                    // is not a surprise reminder
                    addViewModel.setSurprise(false)
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
                    Text(text = "Select date")
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
            END
             */

            /*
             made with Tutorial: https://www.geeksforgeeks.org/time-picker-in-android-using-jetpack-compose/
             edited by Ula Rauch and Anna Leitner
             Beginn
             */
            val timePickerDialog = TimePickerDialog(
                context,
                { _, Hour: Int, Minute: Int ->
                    h = Hour
                    min = Minute
                    addViewModel.setTime(h = h, min = min)
                    //is not a surprise reminder
                    addViewModel.setSurprise(false)
                    isSurprise = false
                }, h, min, false
            )

            Button(
                onClick = {
                    timePickerDialog.show()
                }, modifier = Modifier
                    .padding(20.dp, 5.dp)
                    .fillMaxWidth()
            ) {
                if (isSurprise) {
                    Text(text = "Select time")
                } else {
                    Text(
                        text = "${h.toString().padStart(2, '0')}:${
                            (min).toString().padStart(2, '0')
                        }"
                    )                }

            }
            Spacer(modifier = Modifier.size(16.dp))
            /*
            END
             */


            //Radiobutton for surprise reminder within the next 60 days
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    modifier = Modifier.padding(30.dp, 5.dp),
                    selected = isSurprise,
                    onClick = {
                        if (!isSurprise) {
                            //Reset to current date/time is important to prevent cumulating dates when the radiobutton is clicked several times
                            surpriseDate.set(
                                calNow.get(Calendar.YEAR),
                                calNow.get(Calendar.MONTH),
                                calNow.get(Calendar.DAY_OF_MONTH),
                                calNow.get(Calendar.HOUR_OF_DAY),
                                calNow.get(Calendar.MINUTE)
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
                            //Log.i("Add", "New surprise date: ${date.time}")

                            // set surprise time for reminder
                            y = surpriseDate.get(Calendar.YEAR)
                            m = surpriseDate.get(Calendar.MONTH)
                            d = surpriseDate.get(Calendar.DAY_OF_MONTH)
                            h = surpriseDate.get(Calendar.HOUR_OF_DAY)
                            min = surpriseDate.get(Calendar.MINUTE)
                            addViewModel.setDate(d, m, y)
                            addViewModel.setTime(h, min)
                            addViewModel.setSurprise(true) // is a surprise reminder
                        } else {
                            addViewModel.setSurprise(false)
                        }
                        isSurprise = !isSurprise
                        //Log message visible for presentation
                        Log.i(
                            "Add",
                            "isSurprise in VM is now: ${addViewModel.reminder.value?.isSurprise}"
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
                    addViewModel.setText(value) // update value in viewmodel
                },
                label = { Text(text = "Reminder") },
                placeholder = { Text(text = "Enter Text") },
                modifier = Modifier
                    .padding(20.dp, 5.dp)
                    .fillMaxWidth()
            )
            /*
            END
             */
        }
    }
}
