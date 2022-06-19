package com.example.rememberme.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.work.WorkManager
import com.example.rememberme.models.Reminder
import com.example.rememberme.navigation.RememberScreens
import com.example.rememberme.repositories.RememberRepository
import com.example.rememberme.ui.theme.Green600
import com.example.rememberme.ui.theme.Purple600
import com.example.rememberme.viewmodels.AddRememberViewModel
import com.example.rememberme.viewmodels.AddRememberViewModelFactory
import java.util.*


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
                    Text(
                        text = "New Reminder",

                        )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack, "Arrow Back"
                        )
                    }
                }
            )
        }, floatingActionButton = {
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
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )

            }
        },
        content = {
            ReminderCard(addViewModel = addViewModel, context = context)
        }
    )
}

@Composable
fun ReminderCard(addViewModel: AddRememberViewModel, context: Context) {

    val nowDate = Calendar.getInstance()
    var y: Int by remember { mutableStateOf(nowDate.get(Calendar.YEAR)) }
    var m: Int by remember { mutableStateOf(nowDate.get(Calendar.MONTH)) }
    var d: Int by remember { mutableStateOf(nowDate.get(Calendar.DAY_OF_MONTH)) }
    nowDate.time = Date() //TODO: brauchts das? was passiert, wenn mans weglasst?
    addViewModel.setDate(d = d, m = m, y = y) //set reminder in VM to current date as default

    // Declaring and initializing a calendar
    val nowTime =
        Calendar.getInstance() //TODO: ist das gleiche wie nowDate, wiederverwenden!
    var h = nowTime[Calendar.HOUR_OF_DAY]
    var min = nowTime[Calendar.MINUTE]

    var hReminder: Int by remember { mutableStateOf(nowDate.get(Calendar.HOUR_OF_DAY))}
    var minReminder: Int by remember { mutableStateOf(nowDate.get(Calendar.MINUTE))}
    addViewModel.setTime(h = hReminder, min = minReminder) //set reminder in VM to current time as default

    // Value for storing time as a string
    val mTime = remember { mutableStateOf("") }


    //Log.i("Add", "Calendar.getinstance(): $nowDate")
    /*
    y = nowDate.get(Calendar.YEAR)
    m = nowDate.get(Calendar.MONTH)
    d = nowDate.get(Calendar.DAY_OF_MONTH)
  */

    var text by remember { mutableStateOf("") }
    // add more properties if you need
    val date by remember { mutableStateOf(nowDate) }
    var title by remember { mutableStateOf("") }
    var isSurprise by remember { mutableStateOf(false) }
    val reminder: Reminder? by addViewModel.reminder.observeAsState(null)
    //var isSelected by remember { mutableStateOf(false) }


    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
        ,
        shape = RoundedCornerShape(corner = CornerSize(4.dp)),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalArrangement = Arrangement.Center
        ) {
            //Title
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

            val datePickerDialog = DatePickerDialog(
                context,
                { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                    y = year
                    m =
                        month //months are represented as index https://developer.android.com/reference/java/util/Date.html#Date%28int,%20int,%20int,%20int,%20int,%20int%29
                    d = dayOfMonth
                    addViewModel.setDate(d = d, m = m, y = y)
                    addViewModel.setSurprise(false) // is not a surprise reminder
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

            //Time

            // Creating a TimePicker dialog
            val mTimePickerDialog = TimePickerDialog(
                context,
                { _, mHour: Int, mMinute: Int ->
                    hReminder = mHour
                    minReminder = mMinute
                    addViewModel.setTime(h = hReminder, min = minReminder)
                    addViewModel.setSurprise(false)
                    isSurprise = false
                }, hReminder, minReminder, false
            )

            Button(
                onClick = {
                    mTimePickerDialog.show()
                }, modifier = Modifier
                    .padding(20.dp, 5.dp)
                    .fillMaxWidth()
            ) {
                if (isSurprise) {
                    Text(text = "Select time")
                } else {
                    Text(
                        text = "${hReminder.toString().padStart(2, '0')}:${
                            (minReminder).toString().padStart(2, '0')
                        }"
                    )                }

            }
            Spacer(modifier = Modifier.size(16.dp))

            //Radiobutton for surprise reminder within the next 30 days
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    modifier = Modifier.padding(30.dp, 5.dp),
                    selected = isSurprise,
                    onClick = {
                        if (!isSurprise) {
                            //Reset to current date/time is important to prevent cumulating dates when the radiobutton is clicked several times
                            date.set(
                                nowDate.get(Calendar.YEAR),
                                nowDate.get(Calendar.MONTH),
                                nowDate.get(Calendar.DAY_OF_MONTH),
                                nowDate.get(Calendar.HOUR_OF_DAY),
                                nowDate.get(Calendar.MINUTE)
                            )
                            // set new random date + time within interval minDays - maxDays
                            val minDays = 2
                            val maxDays = 61
                            val randomDays = kotlin.random.Random.nextInt(minDays, maxDays)
                            date.add(Calendar.DAY_OF_MONTH, randomDays)
                            // a limitation to daytime notifications is possible by setting minHours and MaxHours to the desired interval
                            val minHours = 0
                            val maxHours = 24
                            val randomMinutes =
                                kotlin.random.Random.nextInt(minHours, ((maxHours * 60) - 1))
                            date.add(Calendar.MINUTE, randomMinutes)
                            Log.i("Add", "New surprise date: ${date.time}")

                            // set surprise time for reminder
                            y = date.get(Calendar.YEAR)
                            m = date.get(Calendar.MONTH)
                            d = date.get(Calendar.DAY_OF_MONTH)
                            h = date.get(Calendar.HOUR_OF_DAY)
                            min = date.get(Calendar.MINUTE)
                            addViewModel.setDate(d, m, y)
                            addViewModel.setTime(h, min)
                            addViewModel.setSurprise(true) // is a surprise reminder
                        } else {
                            addViewModel.setSurprise(false)
                        }
                        isSurprise = !isSurprise
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

            //Text
            /* von leons Branch*/
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
        }
    }
}
